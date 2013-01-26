(ns checkout.core-test
  (:use clojure.test
        checkout.core))

(def ruleA (->Rule {:item "A" :unit 50 :discount 130 :treshold 3}))
(def ruleB (->Rule {:item "B" :unit 30 :discount 45 :treshold 2}))
(def ruleC (->Rule {:item "C" :unit 20 :discount 20 :treshold 1}))
(def ruleD (->Rule {:item "D" :unit 15 :discount 15 :treshold 1}))

(def rules [ruleA ruleB ruleC ruleD])



(deftest should-test-totals
  (defn price [goods]  
    (def checkout (->Checkout rules []))
    (def items (update-in checkout [:basket ] replace (mapv 
                                                        #(first (-> (scan checkout %) :basket)) 
                                                        (re-seq #"." goods) )))
    (total items)
    )
  
  (testing "Should test totals of products"
    (is (= 0 (price "")))
    (is (= 50 (price "A")))
    (is (= 80 (price "AB")))
    (is (= 115 (price "CDBA")))
    (is (= 100 (price "AA")))
    (is (= 130 (price "AAA")))
    (is (= 180 (price "AAAA")))
    (is (= 230 (price "AAAAA")))
    (is (= 260 (price "AAAAAA")))
    (is (= 160 (price "AAAB")))
    (is (= 175 (price "AAABB")))
    (is (= 190 (price "AAABBD")))
    (is (= 190 (price "DABABA")))
    ))

(deftest test-incremental  
  (def checkout (atom (->Checkout rules [] )))
  (testing "should test the totals of the shopping cart"
    (is (= 0 (total (swap! checkout scan 0))))    
    (is (= 50 (total (swap! checkout scan "A"))))
    (is (= 80 (total (swap! checkout scan "B"))))
    (is (= 130 (total (swap! checkout scan "A"))))
    (is (= 160 (total (swap! checkout scan "A"))))
    (is (= 175 (total (swap! checkout scan "B"))))
    ))