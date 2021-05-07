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

(def travels
  (let [left-boat (fn [l b r] (for [[nl nb] (moves-to-boat l b)] [nl nb r]))
        boat-right (fn [l b r] (let [[nb nr] (move-from-boat b r)] [[l nb nr]]))
        right-boat (fn [l b r] (for [[nr nb] (moves-to-boat r b)] [l nb nr]))
        boat-left (fn [l b r] (let [[nb nl] (move-from-boat b l)] [[nl nb r]]))]
    (cycle [left-boat boat-right right-boat boat-left])))

(defn river-crossing-plan []
  (loop [queue [[[[#{:fox :goose :corn :you} #{:boat} #{}]] travels]]]
    (let [[paths ts] (first queue)
          current (last paths)
          travel (first ts)]
      (if (= (count (last current)) 4)
        paths
        (recur (apply conj
                      (rest queue)
                      (for [next (apply travel current)
                            :when (not ((set paths) next))]
                        [(conj paths next) (rest ts)])))))))
