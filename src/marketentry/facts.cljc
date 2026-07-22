(ns marketentry.facts
  "Per-jurisdiction public-procurement market-entry regulatory catalog
  -- the G2-style spec-basis table the Market-Entry Compliance Governor
  checks every `:jurisdiction/assess` proposal against ('did the advisor
  cite an OFFICIAL public source for this jurisdiction's requirements,
  or did it invent one?').

  Eritrea's real market-entry surface (curl/WebFetch-verified
  2026-07-22) is GENUINELY THIN compared to this family's other
  siblings, and this catalog reports that honestly rather than forcing
  a symmetrical shape. Eritrea has one of the most restrictive
  online-information environments in this fleet: this iteration
  directly attempted every plausible `.gov.er` ministry/authority
  domain it could construct, and ALL of them failed except the Ministry
  of Information's own `shabait.com`:

  - `mti.gov.er`, `www.mti.gov.er`, `moti.gov.er` (Ministry of Trade
    and Industry) -- DNS resolution failure (`Could not resolve
    host`), every variant tried.
  - `www.gov.er`, `www.eritrea.gov.er` -- DNS resolution failure.
  - `eic.gov.er` (a guessed 'Eritrean Investment Center' domain) --
    DNS resolution failure.
  - `erca.gov.er` (a guessed 'Eritrean Revenue and Customs Authority'
    domain, testing the name this catalog's own brief suggested) --
    DNS resolution failure. This iteration could NOT independently
    verify that 'Eritrean Revenue and Customs Authority' is a real
    institution name at all -- see the tax-registration note below,
    where the actually-confirmed name is different.
  - `moj.gov.er`, `centralbank.gov.er` -- DNS resolution failure.
  - `mof.gov.er` (Ministry of Finance) -- DNS RESOLVES, but every
    connection attempt (HTTPS and plain HTTP) either timed out or was
    reset by the peer (`curl: (35) Recv failure: Connection reset by
    peer` on HTTPS, `curl: (28) Connection timed out` on HTTP). The
    domain exists; this iteration could not reach any content on it.
  - `boe.gov.er` (a guessed Bank of Eritrea domain) -- DNS resolves,
    every connection attempt timed out (25s).
  - This session's WebSearch budget was already exhausted before this
    task began (per this iteration's own operating instructions), and
    a DuckDuckGo HTML-search fallback was intentionally NOT attempted
    per the same instructions (known anti-bot risk in this session) --
    so this iteration relied ENTIRELY on directly reachable government
    domains, of which only `shabait.com` (Eritrea Ministry of
    Information's own news/gazette-adjacent outlet, confirmed by its
    own page `<title>Eritrea Ministry Of Information</title>` and its
    own Open Graph `og:site_name`) proved reachable. FAOLEX
    (`fao.org/faolex`) also returned HTTP 403, the same failure mode
    CAF's/other siblings' catalogs already recorded for this session;
    WIPO Lex's guessed jurisdiction-profile URLs returned HTTP 404
    (this iteration did not have time to map WIPO Lex's actual current
    URL scheme for Eritrea and does not want to guess a citation from
    an unread page).

  Despite this, `shabait.com` turned out to be a genuinely useful
  PRIMARY source: it is Eritrea's own Ministry of Information outlet,
  and this iteration found that it has, at least twice, hosted the
  FULL VERBATIM TEXT of an actual numbered Eritrean Proclamation/Legal
  Notice as an ordinary article (not merely news ABOUT a law, the
  law's own operative articles themselves) -- this iteration read
  these in full, directly, via `curl`:

  - **Stamp Duty Proclamation No. 180/2017** -- read in FULL, directly,
    at `shabait.com/2017/03/15/proclamation-no-180-2017/`. Its own
    Article 2 (Definitions), item 6, states: '\"Inland Revenue
    Department\" means the body designated by the Minister to
    implement, inter alia, the provisions of this Proclamation and of
    regulations issued hereunder'; item 8 states: '\"Minister and
    Ministry\" means the Minister and Ministry of Finance,
    respectively'. Article 3 states this Proclamation repeals and
    replaces the prior 'Stamp Duty Proclamation No. 65/1995'. Item 3 of
    Article 2 also cites, in passing, 'Articles 3(8) and (21) of the
    Labour Proclamation of Eritrea No. 118/2001' for the meaning of
    'Collective Agreement and contract of Employment' -- this iteration
    did NOT independently fetch the Labour Proclamation's own primary
    text (only its citation, inside a Proclamation this iteration DID
    read in full).
  - **Legal Notice No. 124/2015 (Legal Tender Nakfa Currency Notes
    Regulations)** -- read in FULL, directly, at
    `shabait.com/2015/11/05/legal-notice-no-124-2015-legal-tender-nakfa-currency-notes-regulations/`.
    Its own Article 1 states these Regulations are issued by the Bank
    of Eritrea 'pursuant to authority vested in it by Articles 5 and 52
    of the Bank of Eritrea Proclamation No. 93/1997'; Article 6 refers
    to the 'Transitional Penal Code of Eritrea'. This iteration did NOT
    independently fetch the Bank of Eritrea Proclamation's own primary
    text (only its citation here). Currency regulation, not market
    entry -- kept out of `catalog` below, mentioned here for
    completeness/honesty about what this iteration actually read.
  - A THIRD numbered instrument, **Legal Notice No. 178/2016** (peasant
    and livestock farm income taxation, replacing the earlier 'Legal
    Notice No. 63/1994', alongside a companion 'Proclamation No.
    125/2016'), was confirmed via a `shabait.com` NEWS ARTICLE ABOUT it
    (`shabait.com/2016/12/02/legal-notice-on-farm-and-livestock-income-tax/`,
    read directly) rather than the Legal Notice's own full text --
    this article ALSO confirms, in passing, that Eritrea publishes an
    official 'Gazette of Eritrean Laws' ('the legal notice that has
    been already published on the Gazette of Eritrean Laws'). This
    iteration could not locate that Gazette's own online presence in
    the time available. Sector-specific (agriculture), not used below.
  - A FOURTH, **Proclamation No. 173/2013** (foreign-currency deposit
    accounts / domestic commercial transactions / currency
    remittance), replacing 'Legal Notice No. 101/2005' and 'Legal
    Notice No. 102/2005', was confirmed via a `shabait.com` news
    article (`shabait.com/about-eritrea/government/` redirected to
    this piece when this iteration tried a guessed 'about
    government' path) -- again news ABOUT the proclamation, not its
    own full text. Monetary, not market entry -- not used below.

  **Business/company registration**: this iteration confirmed the
  Ministry of Trade and Industry (MTI) is a real, currently-operating
  ministry with regional branch offices, directly from TWO independent
  `shabait.com` news articles: (1)
  `shabait.com/2012/05/14/ministry-of-trade-and-industrys-branch-in-northern-red-sea-region-exerting-endeavors-to-ensure-better-services/`
  (2012), which states the branch office in the Northern Red Sea
  region has 'attained customer-oriented effective level' on its
  'license permit control system' and that 'gratifying outcome has
  been registered regarding the issuing and control of trade permits';
  (2)
  `shabait.com/2011/04/24/two-decades-of-revitalizing-trade-and-industrial-undertakings/`
  (2011, MTI's own 20th-anniversary retrospective), which states 'in
  1994 national micro-policy and investment proclamation encouraged
  investment through nominal taxation system[;] [f]ollowing which
  trade license permit regulation which became functional in 1995
  created conducive ground for investment', and separately that 'the
  establishment of standardization institution, professional
  associations, [and] development of the chamber of commerce' also
  took place in 1993-1996. HIGH confidence MTI is the real
  business-registration/trade-licensing authority and that a 'trade
  license permit regulation' became functional in 1995; this iteration
  could NOT find a specific proclamation/legal-notice NUMBER for that
  1995 regulation (or for the 1994 investment proclamation/micro-policy)
  despite multiple additional `shabait.com` searches ('trade license',
  'investment proclamation', 'investment code') -- rather than guess a
  number, `:national-spec` below states the year and cites the article,
  not a specific instrument number.

  **Tax registration**: this iteration confirms the REAL name is
  **Inland Revenue Department** (Ministry of Finance) -- independently
  from TWO sources: (1) Stamp Duty Proclamation No. 180/2017's own
  Article 2(6) definition (quoted above, read directly, HIGH
  confidence); (2) two independent `shabait.com` regional-office news
  articles,
  `shabait.com/2013/07/02/inhabitants-of-goluj-sub-zone-express-satisfaction-with-new-service-by-inland-revenue-department/`
  (2013) and
  `shabait.com/2014/11/13/southern-region-inland-revenue-department-members-receive-training/`
  (2014). This iteration explicitly went looking for whether this
  catalog's own brief's suggested name, 'Eritrean Revenue and Customs
  Authority', is real, and could NOT confirm it anywhere -- `erca.gov.er`
  failed DNS resolution and no `shabait.com` article names any such
  body. A DIFFERENT, separately-named 'Customs Duty Department' is
  confirmed for customs specifically (2010 article,
  `shabait.com/2010/09/24/customs-duty-department-striving-to-control-contraband-activities/`,
  about contraband enforcement) -- this iteration did NOT independently
  confirm which ministry the Customs Duty Department currently sits
  under (Ministry of Finance is the reasonable assumption given Inland
  Revenue Department's placement, but this iteration will not assert
  it as confirmed). Neither a general corporate Income Tax
  Proclamation's own primary text nor its instrument number could be
  found this iteration (only the unrelated, sector-specific Legal
  Notice No. 178/2016 / Proclamation No. 125/2016 for PEASANT/LIVESTOCK
  farm income tax, and the Mining Income Tax Proclamation No. 69/1995
  below, were found).

  **Public procurement (general, government-as-buyer)**: NOT FOUND.
  This iteration could not independently verify ANY general public-
  tender/government-procurement law, regulator or e-procurement portal
  for Eritrea -- no domain comparable to Benin's marches-publics.bj,
  Bhutan's egp.gov.bt or CAF's finances.gouv.cf/marches-publics/* could
  be reached (see the domain-by-domain list above). This is reported
  as an honest access gap, not a claim that Eritrea has no such
  framework.

  **A genuinely Eritrea-specific mechanism THIS ITERATION DID FIND**,
  and which grounds this vertical's flagship check
  (`marketentry.governor`/`marketentry.registry`), is SECTOR-SCOPED to
  mining/mineral exploration rather than general government
  procurement -- honestly reported as such, not stretched to cover a
  general framework this iteration could not verify. A March 2025
  article on `shabait.com`,
  `shabait.com/2025/03/26/eritreas-mining-sector-government-policy-progress-to-date-future-prospects/`,
  bylined 'Eritrean National Mining Corporation (ENAMCO)' (a real,
  government-linked mining enterprise this article itself confirms by
  its own authorship line), was read in full directly (its own
  entry-content, ~62,800 characters). It names, with specific
  citations, what it collectively calls 'the Mining Law' of Eritrea:
  'Minerals Proclamation No 68/1995; Mineral Proclamation 165/2011;
  Regulations on Mining Operations Legal Notice No. 19/1995; and, the
  Mining Income Tax Proclamation No. 69/1995', plus a separately-cited
  'Proclamation No. 157/2006'. Under its own 'Procurement of Goods and
  Services' heading, the article states, IN QUOTATION MARKS attributed
  to the Mining Law itself: 'The Mining Law of Eritrea explicitly
  mandates that all mining and mineral exploration companies conducting
  operations within the country are under a legal obligation to \"give
  preference to domestic goods and services, where they are readily
  available at competitive prices and are of comparable quality.\"'
  It further states (own prose, not a direct quotation, but
  unambiguous) that 'the mining companies are legally mandated to
  formally issue a comprehensive request for offer (RFO) to a minimum
  of three distinct potential suppliers[, t]he sole exception to this
  requirement aris[ing] in situations where a genuine sole supplier
  market condition prevails'.

  This iteration is explicit about the confidence tier here: it read a
  government-published EXPLANATORY ARTICLE that quotes the Mining
  Law's own operative text with specific instrument citations -- it did
  NOT independently fetch any of Minerals Proclamation No. 68/1995,
  Mineral Proclamation No. 165/2011, Legal Notice No. 19/1995 or Mining
  Income Tax Proclamation No. 69/1995's OWN standalone primary-text
  document (no such standalone document could be located this
  iteration in the time available). HIGH confidence on the
  institutional facts and the quoted operative clause (a government
  ministry outlet publishing a signed, cited explanatory article under
  its own domain, quoting specific numbered instruments, is a real and
  meaningfully verifiable source even though it is not the bare
  Proclamation PDF itself); MODERATE confidence that the exact
  wording/scope of the minimum-three-supplier RFO rule matches the
  law's own text precisely (this iteration paraphrased the article's
  own prose here, since the article itself does not put THIS specific
  clause in quotation marks the way it does for the domestic-preference
  clause).

  Coverage is reported HONESTLY (see `coverage`): a jurisdiction not in
  this table has NO spec-basis, full stop -- the advisor must not
  fabricate one, and the governor holds if it tries.")

(def catalog
  "iso3 -> requirement map. `:required-evidence` mirrors the generic
  intake/portal-registration/filing evidence set; `:legal-basis` /
  `:owner-authority` / `:provenance` are the G2 citation the governor
  requires before any `:jurisdiction/assess` proposal can commit. ERI
  deliberately carries NO `:rep-owner-authority` (no representative/
  director exclusion-extension provision could be found for Eritrea in
  this iteration -- see the namespace docstring's honest-scope-narrowing
  note). `:mining-procurement-owner-authority` /
  `:mining-procurement-legal-basis` / `:mining-procurement-criteria` /
  `:mining-procurement-provenance` ground this vertical's flagship
  governor check (`mining-procurement-compliant?`/
  `mining-procurement-noncompliant-claim?` in `marketentry.registry`),
  which is SECTOR-SCOPED (mining/mineral-exploration only) rather than
  a general public-procurement mechanism -- see namespace docstring."
  {"ERI" {:name "Eritrea"
          :owner-authority "Ministry of Trade and Industry (MTI) -- confirmed real via regional branch-office news coverage of its trade license permit issuing/control function; NO general public-tender/government-procurement regulator or e-procurement portal could be independently verified for Eritrea in this iteration (see namespace docstring for the domains attempted and their failure modes)"
          :legal-basis "A 'trade license permit regulation' became functional in 1995, per MTI's own 20th-anniversary retrospective article (this iteration could NOT find a specific proclamation/legal-notice number for this regulation, nor for the '1994 national micro-policy and investment proclamation' the same article says preceded it, despite repeated targeted searches -- the YEARS are confirmed by two independent government-outlet articles, the INSTRUMENT NUMBERS are not)"
          :national-spec "Inland Revenue Department (Ministry of Finance) tax registration, confirmed both by its own definition in Stamp Duty Proclamation No. 180/2017 Art. 2(6) and by independent regional-office news coverage; no dedicated self-service e-procurement portal or online business-registration guichet-unique exists (or could be found) comparable to this family's other siblings -- every other `.gov.er` domain this iteration attempted failed to resolve, timed out, or reset the connection (see namespace docstring)"
          :provenance "https://shabait.com/2012/05/14/ministry-of-trade-and-industrys-branch-in-northern-red-sea-region-exerting-endeavors-to-ensure-better-services/ ; https://shabait.com/2011/04/24/two-decades-of-revitalizing-trade-and-industrial-undertakings/ ; https://shabait.com/2017/03/15/proclamation-no-180-2017/"
          :required-evidence ["MTI trade license permit record (Ministry of Trade and Industry, per its own confirmed regional-branch-office function of issuing/controlling trade permits since a 1995 trade-license-permit regulation this iteration could not independently confirm a specific instrument number for)"
                              "Inland Revenue Department tax registration record (Ministry of Finance, per Stamp Duty Proclamation No. 180/2017 Art. 2(6); this iteration could NOT independently verify a general corporate Income Tax Proclamation's own instrument number, nor the brief's suggested name 'Eritrean Revenue and Customs Authority', which does not appear in any source this iteration reached)"
                              "Mining Law domestic-supplier-preference / minimum-three-supplier-RFO compliance confirmation record, when the engagement declares :mining-sector? true"
                              "Authorized-representative confirmation record"]
          :corporate-number-owner-authority "Inland Revenue Department, Ministry of Finance"
          :corporate-number-legal-basis "Inland Revenue Department's own role is confirmed by Stamp Duty Proclamation No. 180/2017 Art. 2(6) ('\"Inland Revenue Department\" means the body designated by the Minister to implement, inter alia, the provisions of this Proclamation and of regulations issued hereunder'); this iteration did NOT independently fetch a general corporate Income Tax Proclamation's own primary text (only a sector-specific peasant/livestock farm income-tax Legal Notice No. 178/2016 / Proclamation No. 125/2016, and the Mining Income Tax Proclamation No. 69/1995 below, were found)"
          :corporate-number-provenance "https://shabait.com/2017/03/15/proclamation-no-180-2017/"
          :mining-procurement-owner-authority "Ministry of Energy and Mines (MoEM) / Eritrean National Mining Corporation (ENAMCO) -- the mining-sector regulator and state mining enterprise whose own explanatory article grounds this catalog's flagship check; each mining/mineral-exploration operating company applies the domestic-supplier-preference and minimum-supplier-RFO rule to its own procurement"
          :mining-procurement-legal-basis "'The Mining Law of Eritrea' -- Minerals Proclamation No. 68/1995, Mineral Proclamation No. 165/2011, Regulations on Mining Operations Legal Notice No. 19/1995, and Mining Income Tax Proclamation No. 69/1995 (per ENAMCO's own March 2025 explanatory article, which quotes the Mining Law's own text directly: 'all mining and mineral exploration companies conducting operations within the country are under a legal obligation to \"give preference to domestic goods and services, where they are readily available at competitive prices and are of comparable quality\"', and states, in the article's own prose, that mining companies 'are legally mandated to formally issue a comprehensive request for offer (RFO) to a minimum of three distinct potential suppliers', with a genuine sole-supplier-market exception; this iteration did NOT independently fetch any of the four cited Proclamations'/Legal Notice's own standalone primary text -- see namespace docstring for the exact confidence tier)"
          :mining-procurement-criteria {:min-suppliers 3}
          :mining-procurement-provenance "https://shabait.com/2025/03/26/eritreas-mining-sector-government-policy-progress-to-date-future-prospects/"}
   "USA" {:name "United States"
          :owner-authority "U.S. General Services Administration (GSA) / SAM.gov"
          :legal-basis "Federal Acquisition Regulation (FAR); System for Award Management"
          :national-spec "SAM.gov entity registration + NAICS self-certification"
          :provenance "https://sam.gov/"
          :required-evidence ["EIN record"
                              "SAM.gov registration record"
                              "State business registration record"
                              "Authorized-representative record"]}
   "DEU" {:name "Germany"
          :owner-authority "Beschaffungsamt des BMI / e-Vergabe platforms"
          :legal-basis "Gesetz gegen Wettbewerbsbeschränkungen (GWB) / VgV"
          :national-spec "e-Vergabe supplier registration under EU procurement directives"
          :provenance "https://www.evergabe-online.de/"
          :required-evidence ["Handelsregister extract"
                              "e-Vergabe registration record"
                              "USt-IdNr record"
                              "Authorized-representative record"]}})

(defn spec-basis
  "The jurisdiction's requirement map, or nil -- nil means NO spec-basis,
  and the governor must hold any proposal that tries to assess or file
  on it."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report: how many of the requested jurisdictions actually
  have a spec-basis entry. Never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-eri R0: " (count catalog)
                 " jurisdictions seeded with an official spec-basis. "
                 "This is a starting catalog for market-entry navigation, "
                 "not a survey of all ~194 jurisdictions -- extend "
                 "`marketentry.facts/catalog`, never fabricate a "
                 "jurisdiction's requirements.")})))

(defn required-evidence-satisfied?
  "Does `submitted` (a set/coll of evidence keywords or strings) satisfy
  every evidence item listed for `iso3`? Missing spec-basis -> never
  satisfied."
  [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (let [need (count required-evidence)
          have (count (filter (set submitted) required-evidence))]
      (= need have))))

(defn evidence-checklist [iso3]
  (:required-evidence (spec-basis iso3) []))

(defn rep-spec-basis
  "The jurisdiction's representative-related requirement map, or nil when
  this catalog has no such regime. For ERI this is deliberately nil --
  no representative/director exclusion-extension provision could be
  found for Eritrea in this iteration (see namespace docstring)."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:rep-owner-authority sb)
      (select-keys sb [:rep-owner-authority :rep-legal-basis :rep-provenance]))))

(defn corporate-number-spec-basis
  "The jurisdiction's corporate-number / tax-id regime, or nil."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority
                       :corporate-number-legal-basis
                       :corporate-number-provenance]))))

(defn mining-procurement-spec-basis
  "The jurisdiction's Mining Law domestic-supplier-preference /
  minimum-supplier-RFO procurement-compliance regime, or nil. For ERI
  this is real and current -- the flagship check this vertical adds is
  grounded here (Mining Law of Eritrea: Minerals Proclamation No.
  68/1995, Mineral Proclamation No. 165/2011, Legal Notice No. 19/1995,
  Mining Income Tax Proclamation No. 69/1995). SECTOR-SCOPED -- only
  applies to engagements the store marks `:mining-sector? true`."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:mining-procurement-owner-authority sb)
      (select-keys sb [:mining-procurement-owner-authority
                       :mining-procurement-legal-basis
                       :mining-procurement-criteria
                       :mining-procurement-provenance]))))
