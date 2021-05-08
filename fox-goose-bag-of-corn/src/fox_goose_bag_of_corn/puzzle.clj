(ns fox-goose-bag-of-corn.puzzle
  (:require [clojure.set]))

(def start-pos [[#{:fox :goose :corn :you} #{:boat} #{}]])

(defn moves-to-boat [bank boat]
  (remove (fn [[bs _]]
            (#{#{:fox :goose} #{:goose :corn}} bs))
          (map #(vector (clojure.set/difference bank %)
                        (clojure.set/union boat %))
               (map #(into #{} [% :you]) bank))))

(defn move-from-boat [boat bank]
  [#{:boat} (clojure.set/union bank (clojure.set/difference boat #{:boat}))])

(def travels
  (cycle [#(for [[l b] (moves-to-boat %1 %2)] [l b %3])
          #(let [[b r] (move-from-boat %2 %3)] [[%1 b r]])
          #(for [[r b] (moves-to-boat %3 %2)] [%1 b r])
          #(let [[b l] (move-from-boat %2 %1)] [[l b %3]])]))

(defn river-crossing-plan []
  (loop [queue [[start-pos travels]]]
    (let [[paths ts] (first queue)
          current (last paths)
          travel (first ts)]
      (if (= (last current) (first (first start-pos)))
        paths
        (recur (apply conj
                      (rest queue)
                      (for [next (apply travel current)
                            :when (not ((set paths) next))]
                        [(conj paths next) (rest ts)])))))))
