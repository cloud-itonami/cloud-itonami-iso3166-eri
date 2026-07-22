(ns marketentry.registry-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.registry :as registry]))

(deftest engagement-fee-recompute
  (let [e {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 860000.0}]
    (is (== 860000.0 (registry/compute-engagement-fee e)))
    (is (true? (registry/engagement-fee-matches-claim? e))))
  (let [bad {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 999000.0}]
    (is (false? (registry/engagement-fee-matches-claim? bad)))))

(deftest register-draft-and-submit
  (let [d (registry/register-draft "eng-1" "ERI" 0)
        s (registry/register-submit "eng-1" "ERI" 0)]
    (is (= "ERI-DFT-000000" (get d "draft_number")))
    (is (= "ERI-SUB-000000" (get s "submit_number")))
    (is (nil? (get-in d ["certificate" "proof"])))
    (is (= "draft-unsigned" (get-in s ["certificate" "status"])))))

(deftest register-requires-ids
  (is (thrown? Exception (registry/register-draft "" "ERI" 0)))
  (is (thrown? Exception (registry/register-submit "eng-1" "" 0))))

(deftest mining-procurement-compliant-by-supplier-count
  (testing "at least the minimum number of solicited suppliers is sufficient regardless of the sole-supplier-market claim"
    (is (true? (registry/mining-procurement-compliant? {:suppliers-solicited 3})))
    (is (true? (registry/mining-procurement-compliant? {:suppliers-solicited 5})))
    (is (false? (registry/mining-procurement-compliant? {:suppliers-solicited 2})))
    (is (false? (registry/mining-procurement-compliant? {:suppliers-solicited 0})))
    (is (false? (registry/mining-procurement-compliant? {})))))

(deftest mining-procurement-compliant-by-sole-supplier-exception
  (testing "a genuine sole-supplier-market claim is sufficient regardless of supplier count"
    (is (true? (registry/mining-procurement-compliant? {:suppliers-solicited 1 :sole-supplier-market? true})))
    (is (true? (registry/mining-procurement-compliant? {:sole-supplier-market? true})))
    (is (false? (registry/mining-procurement-compliant? {:suppliers-solicited 1 :sole-supplier-market? false}))))
  (testing "both conditions together are still compliant"
    (is (true? (registry/mining-procurement-compliant? {:suppliers-solicited 3 :sole-supplier-market? true})))))

(deftest mining-procurement-noncompliant-claim-is-entity-scope-gated
  (testing "an engagement NOT declared :mining-sector? is never flagged, even if it would fail compliance"
    (is (false? (registry/mining-procurement-noncompliant-claim? {:mining-sector? false :suppliers-solicited 0}))))
  (testing "a mining-sector engagement that fails every compliance branch -> noncompliant claim"
    (is (true? (registry/mining-procurement-noncompliant-claim? {:mining-sector? true
                                                                  :suppliers-solicited 1
                                                                  :sole-supplier-market? false}))))
  (testing "a mining-sector engagement that DOES satisfy compliance -> not flagged"
    (is (false? (registry/mining-procurement-noncompliant-claim? {:mining-sector? true
                                                                   :suppliers-solicited 3})))
    (is (false? (registry/mining-procurement-noncompliant-claim? {:mining-sector? true
                                                                   :suppliers-solicited 0
                                                                   :sole-supplier-market? true})))))
