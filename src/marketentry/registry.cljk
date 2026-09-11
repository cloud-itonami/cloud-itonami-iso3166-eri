(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-procurement market-entry
  filing -- every jurisdiction assigns its own format. This namespace
  does NOT invent one; it builds a jurisdiction-scoped sequence number
  and validates the record's required fields, the same honest,
  non-fabricating discipline `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `mining-procurement-compliant?` / `mining-procurement-noncompliant-claim?`
  are the SAME discipline applied to a genuinely Eritrea-specific
  mechanism this iteration could actually find and cite: 'the Mining
  Law of Eritrea' (Minerals Proclamation No. 68/1995, Mineral
  Proclamation No. 165/2011, Regulations on Mining Operations Legal
  Notice No. 19/1995, Mining Income Tax Proclamation No. 69/1995),
  which -- per a March 2025 Eritrean National Mining Corporation
  (ENAMCO) explanatory article hosted on shabait.com (Eritrea Ministry
  of Information), quoted directly -- mandates that mining/mineral-
  exploration companies 'give preference to domestic goods and
  services, where they are readily available at competitive prices and
  are of comparable quality' when procuring goods/services, and issue
  a request for offer (RFO) to a MINIMUM OF THREE distinct potential
  suppliers before awarding a contract, EXCEPT where a genuine
  sole-supplier market condition prevails. This iteration did not
  independently fetch the four cited Proclamations'/Legal Notice's own
  standalone primary text (see `marketentry.facts` for the exact
  confidence tier) -- the operative rule itself was read directly, in
  quotation marks attributed to the law, on a government-ministry-
  hosted page.

  This is a GENUINELY DIFFERENT check SHAPE from the two sibling
  catalogs this iteration directly compared against before writing this
  one (Central African Republic's Marché réservé workforce-composition
  ELIGIBILITY test, and Comoros's ARMP-exclusion prescription-window
  DECAY test) -- both of those gate whether a BIDDER is eligible to
  participate in something. This check instead gates whether the
  engagement ITSELF, as the buyer in its own supply chain, actually
  COMPLIED WITH A PROCESS QUORUM (did it solicit enough competing
  bids, or genuinely qualify for the one narrow exception) -- a
  PROCESS-COMPLIANCE MINIMUM-QUORUM check, not an eligibility-of-
  candidate test. This iteration has not surveyed every sibling in the
  fleet, so this is stated as a comparison against the two catalogs
  actually read, not a fleet-wide uniqueness claim.

  SECTOR-SCOPED, entity/engagement-scope-gated the same way Bhutan's
  `:foreign-company?`-gated FDI check is described in this family: an
  engagement not declared `:mining-sector? true` is never checked
  against this rule at all, because no general (non-mining) Eritrean
  public-procurement mechanism could be independently verified (see
  `marketentry.facts`).

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real procurement portal. It builds the RECORD an
  operator would keep, not the act of submitting a portal registration
  itself (that is `marketentry.operation`'s `:filing/submit`, always
  human-gated -- see README Actuation)."
  (:require [kotoba.lang.text :as str]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(def ^:private money-scale
  "Sub-minor-unit scale used when comparing two money amounts: 1/10000 of
  a unit. Coarser than double representation error by many orders of
  magnitude, finer than any real currency's minor unit (2 decimals for
  most, 3 for KWD/BHD/OMR, 0 for JPY/KRW)."
  10000)

(defn- money=
  "Exact-at-money-precision equality for two amounts.

  `==` on raw doubles is NOT the right comparison for money. With
  whole-unit fees the two agree, but as soon as an amount carries
  cents the sum `base + rate x months` is routinely not the double
  nearest the true total, and a CORRECT claim compares false: measured
  on this exact shape, 40,989 of 327,060 cent-denominated combinations
  (12.5%) were rejected while being right, against 0 of 327,060 in
  whole units.

  Rounding both sides to `money-scale` before comparing removes the
  representation error while preserving every distinction money can
  actually carry."
  [x y]
  (and (number? x) (number? y)
       (= (Math/round (* money-scale (double x)))
          (Math/round (* money-scale (double y))))))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  ;; nil when any field is not a number: an un-recomputable engagement is
  ;; un-verifiable, which is neither `correct` nor a ClassCastException
  ;; thrown out of the caller.
  (when (and (number? base-fee) (number? monthly-rate) (number? monitoring-months))
    (+ (double base-fee)
       (* (double monthly-rate) (double monitoring-months)))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (money= claimed-fee (compute-engagement-fee engagement)))

(def mining-procurement-thresholds
  "'The Mining Law of Eritrea' (Minerals Proclamation No. 68/1995,
  Mineral Proclamation No. 165/2011, Regulations on Mining Operations
  Legal Notice No. 19/1995, Mining Income Tax Proclamation No. 69/1995),
  per ENAMCO's own March 2025 explanatory article (shabait.com-hosted,
  read directly 2026-07-22): the minimum number of distinct suppliers a
  mining/mineral-exploration company must solicit an RFO from before
  awarding a procurement contract, absent a genuine sole-supplier-market
  exception."
  {:min-suppliers 3})

(defn mining-procurement-compliant?
  "The ground-truth Mining Law procurement-quorum compliance for
  `engagement`, independently recomputed from its own declared
  `:suppliers-solicited` count and `:sole-supplier-market?` claim -- an
  OR of (>= suppliers-solicited min-suppliers) and the genuine
  sole-supplier-market exception. A missing/nil `:suppliers-solicited`
  is treated as zero (does not throw); an engagement declaring neither
  a qualifying count nor the exception is not compliant."
  [{:keys [suppliers-solicited sole-supplier-market?]}]
  (boolean
   (or (>= (long (or suppliers-solicited 0)) (:min-suppliers mining-procurement-thresholds))
       (boolean sole-supplier-market?))))

(defn mining-procurement-noncompliant-claim?
  "Does `engagement` declare `:mining-sector? true` (i.e. it is a
  mining/mineral-exploration operation subject to the Mining Law's own
  procurement mandate) while the INDEPENDENTLY recomputed
  `mining-procurement-compliant?` is false? A non-mining-sector
  engagement is never flagged by this check (entity/engagement-scope-
  gated, the same discipline Bhutan's `:foreign-company?`-gated FDI
  check uses) -- because no general Eritrean public-procurement
  mechanism outside mining could be independently verified (see
  `marketentry.facts`)."
  [{:keys [mining-sector?] :as engagement}]
  (boolean (and mining-sector? (not (mining-procurement-compliant? engagement)))))

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a portal registration
  package. Pure function -- does not touch any real procurement
  portal."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a portal
  registration (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
