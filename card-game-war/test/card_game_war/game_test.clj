(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))


;; fill in  tests for your game
(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (is (= 0 (play-round [:spade 10] [:club 2])))
    (is (= 1 (play-round [:club 2] [:diamond 10])))
    (is (= nil (play-round [:diamond 5] [:heart 5]))))
  (testing "queens are higher rank than jacks"
    (is (= 0 (play-round [:spade :queen] [:club :jack]))))
  (testing "kings are higher rank than queens"
    (is (= 0 (play-round [:spade :king] [:club :queen]))))
  (testing "aces are higher rank than kings"
    (is (= 0 (play-round [:spade :ace] [:club :king])))))

(deftest test-play-game
  (testing "the player loses when they run out of cards"
    (contains? [0 1 nil]
               (play-game [[:spade 4]
                           [:spade :ace]
                           [:spade 6]
                           [:club 4]
                           [:club :ace]
                           [:club 6]
                           [:diamond 8]]
                          [[:spade 8]
                           [:spade :king]
                           [:diamond 6]
                           [:heart 8]
                           [:heart :king]
                           [:heart 6]
                           [:heart 9]]))))

(deftest random-play-game
  (dotimes [n 10]
    (println "=====")
    (println "Random game No." n)
    (testing
      (let [shuffled-pairs (partition 2 (shuffle cards))
            player0-cards (map first shuffled-pairs)
            player1-cards (map second shuffled-pairs)]
        (contains? [0 1 nil]
                   (play-game player0-cards player1-cards))))))
