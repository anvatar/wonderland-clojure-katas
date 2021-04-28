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

(defn decipher [cipher message]
  "decypherme")

