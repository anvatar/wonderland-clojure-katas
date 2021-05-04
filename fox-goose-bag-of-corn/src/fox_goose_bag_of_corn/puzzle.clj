(ns fox-goose-bag-of-corn.puzzle)

(def start-pos [[[:fox :goose :corn :you] [:boat] []]])

(def the-solution
  [[[:fox :goose :corn :you] [:boat] []]
   [[:fox :corn] [:boat :goose :you] []]
   [[:fox :corn] [:boat] [:goose :you]]
   [[:fox :corn] [:boat :you] [:goose]]
   [[:fox :corn :you] [:boat] [:goose]]
   [[:fox] [:boat :corn :you] [:goose]]
   [[:fox] [:boat] [:goose :corn :you]]
   [[:fox] [:boat :goose :you] [:corn]]
   [[:fox :goose :you] [:boat] [:corn]]
   [[:goose] [:boat :fox :you] [:corn]]
   [[:goose] [:boat] [:corn :fox :you]]
   [[:goose] [:boat :you] [:corn :fox]]
   [[:goose :you] [:boat] [:corn :fox]]
   [[] [:boat :goose :you] [:corn :fox]]
   [[] [:boat] [:corn :fox :goose :you]]])

(defn moves-to-boat [bank boat]
  (remove (fn [[bs _]]
            (or (= (set bs) #{:fox :goose})
                (= (set bs) #{:goose :corn})))
          (map (fn [ms]
                 [(set (remove ms bank))
                  (set (concat ms boat))])
               (map (fn [b] (into #{} [b :you])) bank))))

(defn move-from-boat [boat bank]
  [#{:boat} (set (concat bank (remove #{:boat} boat)))])

(defn river-crossing-plan []
  the-solution)
