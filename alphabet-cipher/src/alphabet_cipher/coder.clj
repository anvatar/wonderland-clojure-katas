(ns alphabet-cipher.coder)

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(def encode-rows
  (map seq
       (iterate #(str (subs % 1) (subs % 0 1))
                alphabet)))

(def encode-table
  (into {}
        (for [[r row] (zipmap alphabet encode-rows)
              [c letter] (zipmap alphabet row)]
             [[r c] letter])))

(defn encode [keyword message]
  (apply str
         (map (fn [r c] (get encode-table [r c]))
              (apply concat (repeat keyword))
              message)))

(defn decode [keyword message]
  (apply str
         (map #(-> (filter (fn [[[r _] x]] (and (= r %1) (= x %2))) encode-table)
                   first
                   first
                   second)
              (apply concat (repeat keyword))
              message)))

(defn shortest-prefix [stretched]
  (last (filter #(= (take (count stretched) (apply concat (repeat %))) stretched)
                (take-while not-empty (iterate #(drop-last %) stretched)))))

(defn decipher [cipher message]
  (apply str
         (shortest-prefix
           (map #(-> (filter (fn [[[_ c] x]] (and (= c %1) (= x %2))) encode-table)
                     first
                     first
                     first)
                message
                cipher))))
