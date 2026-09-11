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

;; ---------------------------------------------------------------------------
;; Money is compared at money precision, not at double precision
;; ---------------------------------------------------------------------------

(deftest whole-unit-fees-were-already-correct-and-stay-correct
  (testing "the seeded shape: base + rate x months in whole currency units"
    (is (registry/engagement-fee-matches-claim?
         {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12
           :claimed-fee 860000.0}))))

(deftest cent-denominated-fees-are-no-longer-rejected-while-correct
  (testing "`(== (double claimed) (+ (double base) (* (double rate) (double months))))`
            rejected CORRECT totals once an amount carried cents -- 40,989 of
            327,060 combinations (12.5%), against 0 of 327,060 in whole units"
    (let [bad (for [m (range 1 37)
                    bc (range 10000 90000 2100)
                    rc (range 500 6000 210)
                    :let [truth (/ (+ bc (* rc m)) 100.0)]
                    :when (not (registry/engagement-fee-matches-claim?
                                {:base-fee (/ bc 100.0) :monthly-rate (/ rc 100.0)
                                  :monitoring-months m :claimed-fee truth}))]
                [m (/ bc 100.0) (/ rc 100.0) truth])]
      (is (empty? bad) (str "false rejections: " (count bad) " e.g. " (first bad))))))

(deftest a-genuinely-wrong-fee-is-still-caught
  (testing "rounding to money precision must not blunt the check"
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12
                :claimed-fee 860000.01})))
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12
                :claimed-fee 859999.99})))))

(deftest an-unverifiable-fee-never-matches
  (testing "un-verifiable is not the same as correct, and not a crash"
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12})))
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee "500000" :monthly-rate 30000 :monitoring-months 12
                :claimed-fee 860000.0})))
    (is (nil? (registry/compute-engagement-fee {:base-fee 500000 :monthly-rate 30000})))))
