(ns wonderland-number.finder)

(defn wonderland-number []
  (first (reduce (fn [sq n]
                   (filter #(= (set (str %))
                               (set (str (* n %))))
                           sq))
                 (range 100000 1000000)
                 (range 2 7))))
