(ns tiny-maze.solver)

(defn- escaped? [maze]
  (= :x
     (last (last maze))))

(defn- mark [maze [r c]]
  (assoc maze r
              (assoc (nth maze r) c
                                  :x)))

(defn- next-positions [maze [r c]]
  (for [nr (range (count maze))
        nc (range (count (first maze)))
        :when (and (some? (#{0 :E} (get-in maze [nr nc])))
                   (= [0 1]
                      (sort (map #(Math/abs ^int %)
                                 [(- nr r) (- nc c)]))))]
    [nr nc]))

(defn solve-maze [maze]
  (loop [queue [[(mark maze [0 0]) [0 0]]]]
    (let [[mz pos] (first queue)]
      (if (escaped? mz)
        mz
        (recur (apply conj
                      (rest queue)
                      (map #(vector (mark mz %) %)
                           (next-positions mz pos))))))))
