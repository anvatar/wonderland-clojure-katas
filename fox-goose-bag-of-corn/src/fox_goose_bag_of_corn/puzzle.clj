(ns fox-goose-bag-of-corn.puzzle
  (:require [clojure.set]))

(def start-pos [[#{:fox :goose :corn :you} #{:boat} #{}]])

(defn moves-to-boat [bank boat]
  (for [p bank
        :let [ps (into #{} [p :you])
              new-bank (clojure.set/difference bank ps)
              new-boat (clojure.set/union boat ps)]
        :when (not (#{#{:fox :goose} #{:goose :corn}} new-bank))]
    [new-bank new-boat]))

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
