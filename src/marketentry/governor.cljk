(ns marketentry.governor
  "Market-Entry Compliance Governor -- the independent compliance layer
  that earns the MarketEntry-LLM the right to commit. The LLM has no
  notion of Eritrean procurement law, whether a claimed engagement fee
  actually equals base + months x rate, whether the engagement's own
  declared mining-sector procurement process actually solicited the
  Mining Law's own minimum-three-supplier RFO (or genuinely qualifies
  for the sole-supplier-market exception), whether an Inland Revenue
  Department tax record has been verified for a filing that requires
  it, or when a draft stops being a draft and becomes a real-world
  filing submission, so this MUST be a separate system able to *reject*
  a proposal and fall back to HOLD.

  `:itonami.blueprint/governor` is `:market-entry-compliance-governor`
  (shared family keyword on blueprints).

  This blueprint's own text (docs/business-model.md; any actual portal
  registration or filing submission requires Market-Entry Compliance
  Governor clearance and always escalates to human sign-off; a false
  or fabricated regulatory-requirement claim is a HARD hold) names
  exactly the checks below.

  Six checks, in priority order, ALL HARD violations: a human
  approver CANNOT override them. The confidence/actuation gate is
  SOFT: it asks a human to look (low confidence / actuation), and the
  human may approve -- but see `marketentry.phase`: for `:stake
  :actuation/draft-filing`/`:actuation/submit-filing` NO phase ever
  allows auto-commit either. Two independent layers agree that
  actuation is always a human call.

    1. Spec-basis                  -- did the jurisdiction proposal cite
                                       an OFFICIAL source
                                       (`marketentry.facts`), or invent
                                       one?
    2. Evidence incomplete         -- for `:filing/draft`/
                                       `:filing/submit`, has the
                                       jurisdiction actually been
                                       assessed with a full evidence
                                       checklist on file?
    3. Mining-procurement
       noncompliant                  -- for `:filing/submit`, when the
                                       engagement declares
                                       `:mining-sector? true` (i.e. it
                                       is a mining/mineral-exploration
                                       operation subject to Eritrea's
                                       own Mining Law), INDEPENDENTLY
                                       recompute whether the
                                       engagement's own declared
                                       `:suppliers-solicited` count (or
                                       `:sole-supplier-market?` claim)
                                       actually satisfies the Mining
                                       Law's own minimum-three-supplier
                                       RFO mandate, and HARD-hold if
                                       not. FLAGSHIP check for this
                                       jurisdiction -- a PROCESS-
                                       COMPLIANCE MINIMUM-QUORUM check
                                       (did the engagement itself, as
                                       buyer, solicit enough competing
                                       bids), a shape different from
                                       both sibling catalogs this
                                       iteration directly compared
                                       against (CAF's Marché réservé
                                       bidder-ELIGIBILITY test, and
                                       Comoros's ARMP-exclusion
                                       prescription-window DECAY test)
                                       -- see `marketentry.facts` /
                                       `marketentry.registry`.
                                       SECTOR-SCOPED: a non-mining
                                       engagement is never checked
                                       against this rule (no general
                                       Eritrean public-procurement
                                       mechanism could be independently
                                       verified -- see
                                       `marketentry.facts`).
    4. Engagement fee mismatch     -- for `:filing/submit`,
                                       INDEPENDENTLY recompute whether
                                       the engagement's own `:claimed-
                                       fee` equals `base-fee +
                                       monthly-rate x monitoring-
                                       months` -- honest reapplication
                                       of the ground-truth-recompute
                                       discipline sibling actors use.
    5. Inland Revenue Department
       record unverified            -- for `:filing/submit`, when the
                                       engagement declares
                                       `:requires-inland-revenue-record?
                                       true`, INDEPENDENTLY check
                                       `:inland-revenue-record-verified?`.
                                       CONDITIONAL on the engagement's
                                       own ground truth. Grounded in
                                       the Inland Revenue Department,
                                       Ministry of Finance (see
                                       `marketentry.facts` -- this is
                                       the independently-confirmed real
                                       name; this catalog's own brief
                                       suggested 'Eritrean Revenue and
                                       Customs Authority', which this
                                       iteration could NOT verify
                                       exists anywhere).
    6. Confidence floor / actuation
       gate                          -- LLM confidence below threshold,
                                       OR the op is `:filing/draft`/
                                       `:filing/submit` (REAL acts)
                                       -> escalate.

  Two more guards, double-draft/double-submit prevention, are enforced
  off dedicated `:drafted?`/`:submitted?` facts (never a `:status`
  value)."
  (:require [marketentry.facts :as facts]
            [marketentry.registry :as registry]
            [marketentry.store :as store]))

(def confidence-floor 0.6)

(def high-stakes
  "Stakes grave enough to always require a human, even when clean.
  Drafting a real portal package and submitting a real portal
  registration are the two real-world actuation events this actor
  performs."
  #{:actuation/draft-filing :actuation/submit-filing})

;; ----------------------------- checks -----------------------------

(defn- spec-basis-violations
  "A `:jurisdiction/assess` (or `:filing/draft`/`:filing/submit`)
  proposal with no spec-basis citation is a HARD violation -- never
  invent a jurisdiction's market-entry requirements."
  [{:keys [op]} proposal]
  (when (contains? #{:jurisdiction/assess :filing/draft :filing/submit} op)
    (let [value (:value proposal)]
      (when (or (empty? (:cites proposal))
                (and (contains? value :spec-basis) (nil? (:spec-basis value))))
        [{:rule :no-spec-basis
          :detail "公式spec-basisの引用が無い提案は法域要件として扱えない"}]))))

(defn- evidence-incomplete-violations
  "For `:filing/draft`/`:filing/submit`, the jurisdiction's required
  registration evidence must actually be satisfied."
  [{:keys [op subject]} st]
  (when (contains? #{:filing/draft :filing/submit} op)
    (let [e (store/engagement st subject)
          assessment (store/assessment-of st subject)]
      (when-not (and assessment
                     (facts/required-evidence-satisfied?
                      (:jurisdiction e) (:checklist assessment)))
        [{:rule :evidence-incomplete
          :detail "法域の必要書類(MTI営業許可証/内国歳入局納税記録/鉱業法調達適合確認/代理人確認等)が充足していない状態での提案"}]))))

(defn- mining-procurement-noncompliant-violations
  "For `:filing/submit`, INDEPENDENTLY recompute whether the
  engagement's own declared mining-sector procurement process
  satisfies the Mining Law's own minimum-three-supplier RFO mandate --
  the flagship check this vertical adds. The minimum supplier count is
  ALWAYS sourced from `marketentry.facts`/`marketentry.registry`, never
  hardcoded here."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (registry/mining-procurement-noncompliant-claim? e)
        [{:rule :mining-procurement-noncompliant
          :detail (str subject " は鉱業法(Minerals Proclamation No. 68/1995等)の"
                      "国内供給者優先/最低3社見積(RFO)義務を、"
                      "独立再計算(suppliers-solicited=" (:suppliers-solicited e)
                      ", sole-supplier-market?=" (:sole-supplier-market? e)
                      ")の結果満たしておらず、提出提案は進められない")}]))))

(defn- engagement-fee-mismatch-violations
  "For `:filing/submit`, INDEPENDENTLY recompute whether the
  engagement's own claimed fee equals base + months x rate."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when-not (registry/engagement-fee-matches-claim? e)
        [{:rule :engagement-fee-mismatch
          :detail (str subject " の申告手数料(" (:claimed-fee e)
                      ")が独立再計算値(" (registry/compute-engagement-fee e) ")と一致しない")}]))))

(defn- inland-revenue-record-unverified-violations
  "For `:filing/submit`, when the engagement declares
  `:requires-inland-revenue-record? true`, INDEPENDENTLY check
  `:inland-revenue-record-verified?` -- CONDITIONAL on the engagement's
  own ground truth. Grounded in the Inland Revenue Department, Ministry
  of Finance."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (and (true? (:requires-inland-revenue-record? e))
                 (not (true? (:inland-revenue-record-verified? e))))
        [{:rule :inland-revenue-record-unverified
          :detail (str subject " は内国歳入局(Inland Revenue Department, Ministry of Finance)"
                      "税務記録の確認を要するが未確認 -- 提出提案は進められない")}]))))

(defn- already-drafted-violations
  "For `:filing/draft`, refuses to draft the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/draft)
    (when (store/engagement-already-drafted? st subject)
      [{:rule :already-drafted
        :detail (str subject " は既にドラフト済み")}])))

(defn- already-submitted-violations
  "For `:filing/submit`, refuses to submit the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (when (store/engagement-already-submitted? st subject)
      [{:rule :already-submitted
        :detail (str subject " は既に提出済み")}])))

(defn check
  "Censors a MarketEntry-LLM proposal against the governor rules.
  Returns {:ok? bool :violations [..] :confidence c :escalate? bool
  :high-stakes? bool :hard? bool}."
  [request _context proposal st]
  (let [hard (into []
                   (concat (spec-basis-violations request proposal)
                           (evidence-incomplete-violations request st)
                           (mining-procurement-noncompliant-violations request st)
                           (engagement-fee-mismatch-violations request st)
                           (inland-revenue-record-unverified-violations request st)
                           (already-drafted-violations request st)
                           (already-submitted-violations request st)))
        conf (:confidence proposal 0.0)
        low? (< conf confidence-floor)
        stakes? (boolean (high-stakes (:stake proposal)))
        hard? (boolean (seq hard))]
    {:ok?          (and (not hard?) (not low?) (not stakes?))
     :violations   hard
     :confidence   conf
     :hard?        hard?
     :escalate?    (and (not hard?) (or low? stakes?))
     :high-stakes? stakes?}))

(defn hold-fact
  "The audit fact written when a proposal is rejected (HOLD)."
  [request context verdict]
  {:t          :governor-hold
   :op         (:op request)
   :actor      (:actor-id context)
   :subject    (:subject request)
   :disposition :hold
   :basis      (mapv :rule (:violations verdict))
   :violations (:violations verdict)
   :confidence (:confidence verdict)})
