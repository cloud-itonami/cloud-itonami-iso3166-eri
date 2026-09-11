(ns culture.facts
  "Country-level regional-culture catalog for Eritrea (ERI) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"ERI"
   [{:culture/id "eri.dish.injera"
     :culture/name "Injera"
     :culture/country "ERI"
     :culture/kind :dish
     :culture/summary "Fermented flatbread that is a staple in both Ethiopia and Eritrea, of shared significance to the two nations' cuisines."
     :culture/url "https://en.wikipedia.org/wiki/Injera"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "eri.dish.zigni"
     :culture/name "Zigni"
     :culture/country "ERI"
     :culture/kind :dish
     :culture/summary "Meat stew from Eritrea and Ethiopia made with tomatoes, red onions and berbere spices, considered to be the national dish of Eritrea."
     :culture/url "https://en.wikipedia.org/wiki/Zigni"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "eri.dish.shahan-ful"
     :culture/name "Shahan ful"
     :culture/country "ERI"
     :culture/kind :dish
     :culture/summary "Fava bean dish common across the Horn of Africa, with Eritrea among the places where it is served."
     :culture/url "https://en.wikipedia.org/wiki/Shahan_ful"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "eri.dish.himbasha"
     :culture/name "Himbasha"
     :culture/country "ERI"
     :culture/kind :dish
     :culture/summary "Slightly sweet Ethiopian and Eritrean celebration bread, popular in Eritrean cuisine and often served at special occasions."
     :culture/url "https://en.wikipedia.org/wiki/Himbasha"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "eri.beverage.coffee-ceremony"
     :culture/name "Coffee ceremony of Ethiopia and Eritrea"
     :culture/country "ERI"
     :culture/kind :beverage
     :culture/summary "Coffee culture practiced in Ethiopia and Eritrea: green beans are roasted, ground and brewed in a clay pot, served in multiple rounds as a daily social gathering."
     :culture/url "https://en.wikipedia.org/wiki/Coffee_ceremony_of_Ethiopia_and_Eritrea"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "eri.festival.meskel"
     :culture/name "Meskel"
     :culture/country "ERI"
     :culture/kind :festival
     :culture/summary "Religious holiday of the Eritrean Orthodox Tewahedo Church and other Christian denominations in Eritrea and Ethiopia, commemorating the discovery of the True Cross with annual bonfire ceremonies."
     :culture/url "https://en.wikipedia.org/wiki/Meskel"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "eri.heritage.asmara"
     :culture/name "Asmara"
     :culture/country "ERI"
     :culture/kind :heritage
     :culture/summary "Capital of Eritrea, designated a UNESCO World Heritage Site in 2017 as 'Asmara: A Modernist African City' for its early modernist urbanism."
     :culture/url "https://en.wikipedia.org/wiki/Asmara"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-eri culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "ERI"))
                 " ERI entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
