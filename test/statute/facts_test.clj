(ns statute.facts-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [statute.facts :as facts]))

(deftest eri-has-spec-basis
  (let [sb (facts/spec-basis "ERI")]
    (is (= 2 (count sb)))
    (is (every? #(str/starts-with? (:statute/url %) "https://") sb))
    (is (every? :statute/law-number sb))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["ERI" "JPN" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ATL" "JPN"] (:missing-jurisdictions c)))))

(deftest by-topic-filters
  (is (= ["eri.mining-law"]
         (mapv :statute/id (facts/by-topic "ERI" :mining))))
  (is (= ["eri.stamp-duty-proclamation-180-2017"]
         (mapv :statute/id (facts/by-topic "ERI" :taxation))))
  (is (empty? (facts/by-topic "ERI" :labor))
      "Labour Proclamation No. 118/2001's own primary text could not be independently verified this iteration -- honestly absent, see namespace docstring")
  (is (empty? (facts/by-topic "ATL" :labor))))
