(ns doublets.solver
  (:require [clojure.java.io :as io]))

(def words (-> "words.edn"
               (io/resource)
               (slurp)
               (read-string)))

(defn- distance [word1 word2]
  (reduce +
          (map #(if (= %1 %2) 0 1)
               word1
               word2)))

(defn- doublet [word]
  (filter #(and (= (distance word %) 1)
                (= (count word) (count %)))
          words))

(defn doublets [word1 word2]
  (loop [queue [[word1]]]
    (if (empty? queue)
      []
      (let [candidates (first queue)
            current (last candidates)]
        (if (= current word2)
          candidates
          (recur (apply conj
                        (rest queue)
                        (for [next (doublet current)
                              :when (empty? (filter #{next} candidates))]
                          (conj candidates next)))))))))
