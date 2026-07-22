(ns statute.facts
  "General-law compliance catalog for Eritrea (ERI) -- extends this
  repo's existing `marketentry.facts` (public-procurement market-entry
  only, narrow scope) with a second, orthogonal catalog of statutes a
  company operating in this jurisdiction must generally track for
  compliance. Mirrors cloud-itonami-iso3166-jpn/-deu/-bgr/-aze/-alb/
  -arm/-atg/-ben/-btn/-caf's `statute.facts` (ADR-2607141700,
  cloud-itonami-compliance-fact-federation).

  Every entry cites an OFFICIAL government-hosted URL that this
  iteration actually fetched and read -- never fabricated. Eritrea's
  own government has an extremely thin, hard-to-reach online footprint
  (see `marketentry.facts` for the full list of domains attempted and
  their failure modes); the ONE domain that proved reachable,
  `shabait.com` (Eritrea Ministry of Information), turned out to
  directly host the full verbatim text of at least two real, numbered
  Eritrean laws as ordinary articles -- this catalog cites those, and
  is explicit about which entries rest on a law's OWN primary text
  (read in full) versus a government-published article ABOUT a law
  (news coverage, not the law's own text).

  - **Stamp Duty Proclamation No. 180/2017** -- this iteration read
    its FULL operative text directly at
    `shabait.com/2017/03/15/proclamation-no-180-2017/` (all 7 articles:
    short title, definitions, repeal of the prior Stamp Duty
    Proclamation No. 65/1995, the chargeable-instrument schedule,
    stamp-duty rates, mode of valuation, and effective date). HIGH
    confidence -- this is the law's own primary text, not a summary.
  - **'The Mining Law of Eritrea'** (Minerals Proclamation No. 68/1995,
    Mineral Proclamation No. 165/2011, Regulations on Mining Operations
    Legal Notice No. 19/1995, Mining Income Tax Proclamation No.
    69/1995) -- this iteration did NOT independently fetch any of these
    four instruments' own standalone primary text. It instead read, in
    full, a March 2025 Eritrean National Mining Corporation (ENAMCO)
    explanatory article hosted on `shabait.com`
    (`shabait.com/2025/03/26/eritreas-mining-sector-government-policy-progress-to-date-future-prospects/`)
    that names these four instruments collectively and quotes several
    of their operative clauses directly, in quotation marks (e.g. 'all
    mineral resources are public property which can make a significant
    contribution to the economic development of the country'; 'give
    preference to domestic goods and services, where they are readily
    available at competitive prices and are of comparable quality').
    MODERATE-to-HIGH confidence on the institutional facts and quoted
    clauses (a government ministry's own outlet publishing a
    signed, cited explanatory article that quotes specific numbered
    instruments is a real and meaningfully verifiable source); this
    iteration explicitly does NOT claim to have verified each
    instrument's own complete text or every provision therein.
  - **Labour Proclamation of Eritrea No. 118/2001**: this iteration did
    NOT independently locate or fetch this Proclamation's own primary
    text. Its existence, number and year are confirmed only via its
    OWN citation inside Stamp Duty Proclamation No. 180/2017's own
    Article 2, item 3 ('\"Collective Agreement and contract of
    Employment\" shall have the respective meanings given to them by
    Articles 3 (8) and (21) of the Labour Proclamation of Eritrea No.
    118/2001') -- a citation this iteration read directly, inside a
    Proclamation whose full text this iteration DID read. Because this
    iteration has not read the Labour Proclamation's own content
    (only that it exists, under this exact number/name), `by-topic`
    below deliberately returns an EMPTY vector for `:labor` rather than
    a thin, content-free entry -- an honest gap, not an omission by
    design (see `coverage`).
  - Companies/commercial-entity law: this iteration searched
    `shabait.com` for 'commercial registration' and general company-law
    terms and found NO Eritrean Companies Act / Commercial Code primary
    text or even a news article naming one with a specific instrument
    number. Eritrea is not an OHADA (or comparable regional-instrument)
    member state, so unlike this family's CAF/Comoros/Benin catalogs
    there is no supranational Uniform Act to fall back on either. This
    is an honest, explicitly-reported GAP, not a claim that Eritrea has
    no company law -- it almost certainly does, this iteration simply
    could not read its own primary text or confirm its exact law
    number/date through any source reachable in this session.
  - A general corporate Income Tax Proclamation was ALSO searched for
    and not found -- only a sector-specific peasant/livestock farm
    income-tax Legal Notice No. 178/2016 / Proclamation No. 125/2016
    (confirmed via a `shabait.com` news article, not the law's own
    text, and not included below as it is agricultural, not a general
    corporate-tax citation) and the Mining Income Tax Proclamation No.
    69/1995 (bundled into the Mining Law entry above) were found.

  A law not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of statute entries. `:statute/url` + `:statute/law-number`
  are the citation the governor requires before any compliance-fact
  proposal referencing this law can commit. ERI's catalog is smaller
  than some siblings' -- this reflects an honest coverage gap
  (Companies Act, general corporate Income Tax Proclamation and the
  Labour Proclamation's own primary text could not be independently
  verified this iteration, see namespace docstring), not a design
  choice to omit them."
  {"ERI"
   [{:statute/id "eri.stamp-duty-proclamation-180-2017"
     :statute/title "A Proclamation to Provide for the Payment of Stamp Duty (Stamp Duty Proclamation No. 180/2017)"
     :statute/jurisdiction "ERI"
     :statute/kind :law
     :statute/law-number "Proclamation No. 180/2017 -- this iteration independently read its own full primary text directly at shabait.com (Eritrea Ministry of Information); it repeals and replaces the prior Stamp Duty Proclamation No. 65/1995 (own Article 3), and its own Article 2 designates the Inland Revenue Department, Ministry of Finance, as the body implementing it (HIGH confidence, primary text read in full)"
     :statute/url "https://shabait.com/2017/03/15/proclamation-no-180-2017/"
     :statute/url-provenance :official-shabait-com-ministry-of-information
     :statute/enacted-date "2017-03-15"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:taxation :stamp-duty}}
    {:statute/id "eri.mining-law"
     :statute/title "The Mining Law of Eritrea (Minerals Proclamation No. 68/1995; Mineral Proclamation No. 165/2011; Regulations on Mining Operations Legal Notice No. 19/1995; Mining Income Tax Proclamation No. 69/1995)"
     :statute/jurisdiction "ERI"
     :statute/kind :law
     :statute/law-number "Minerals Proclamation No. 68/1995 + Mineral Proclamation No. 165/2011 + Legal Notice No. 19/1995 + Mining Income Tax Proclamation No. 69/1995 -- this iteration did NOT independently fetch any of these four instruments' own standalone primary text; their existence, exact numbers, and several quoted operative clauses (domestic-supplier-preference procurement mandate; public-property characterization of mineral resources) are confirmed via a March 2025 Eritrean National Mining Corporation (ENAMCO) explanatory article hosted on shabait.com, read in full directly (MODERATE-to-HIGH confidence -- see namespace docstring for the exact tier)"
     :statute/url "https://shabait.com/2025/03/26/eritreas-mining-sector-government-policy-progress-to-date-future-prospects/"
     :statute/url-provenance :official-shabait-com-ministry-of-information
     :statute/enacted-date "1995-01-01"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:mining :natural-resources :procurement}}]})

(defn spec-basis
  "The jurisdiction's statute vector, or nil -- nil means NO spec-basis
  for that jurisdiction yet."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report, same shape/discipline as `marketentry.facts/coverage`:
  never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-eri statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "ERI")) " ERI statute(s) seeded with an "
                 "official citation (Companies Act, general corporate Income "
                 "Tax Proclamation and the Labour Proclamation's own primary "
                 "text could not be independently verified this iteration -- "
                 "an honest gap, not an omission by design). Extend "
                 "`statute.facts/catalog`, never fabricate a law-id or URL.")})))

(defn by-topic
  "Statutes for `iso3` tagged with `topic` (e.g. :labor, :mining)."
  [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
