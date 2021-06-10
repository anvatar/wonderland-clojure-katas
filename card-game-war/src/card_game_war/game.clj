(ns card-game-war.game)

;; feel free to use these cards or use your own data structure
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [suit suits
        rank ranks]
    [suit rank]))

(defn play-round [[_ rank0] [_ rank1]]
  (let [indexes (zipmap ranks (range))
        [index0 index1] (map #(get indexes %) [rank0 rank1])]
    (cond (> index0 index1) 0
          (< index0 index1) 1
          :else nil)))

(defn- show-cards [cards]
  (println (take 5 cards) " (total " (count cards) " cards)"))

(defn play-game [player0-cards player1-cards]
  (loop [cards0 player0-cards
         cards1 player1-cards
         war-cards []]
    (println "-----")
    (doall (map show-cards [cards0 cards1 war-cards]))

    (cond (every? empty? [cards0 cards1]) nil
          (empty? cards1) 0
          (empty? cards0) 1
          :else (case (play-round (first cards0) (first cards1))
                  0 (recur (concat (rest cards0) (shuffle (concat war-cards (map first [cards0 cards1]))))
                           (rest cards1)
                           [])
                  1 (recur (rest cards0)
                           (concat (rest cards1) (shuffle (concat war-cards (map first [cards0 cards1]))))
                           [])
                  nil (recur (drop 4 cards0)
                             (drop 4 cards1)
                             (concat war-cards (take 4 cards0) (take 4 cards1)))))))
