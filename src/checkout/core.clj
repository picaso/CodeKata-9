(ns checkout.core
  (:gen-class))



(defprotocol Checkout-Intf
  (total [this])
  (scan [this item]))

(defprotocol Rule-Intf
  (apply-discount [this]))


(defn get-rule [rules key]
  (first (filter #(= ((-> % :args) :item) key) 
                 (-> rules))))

(defn attach-rule [rules grouped-item]
  (let [rule_ (get-rule rules 
                        (first grouped-item))]
    (if (not (nil? rule_))
      (update-in rule_ [:args] 
                 assoc :good grouped-item )))
    )

(defn calc [enriched-rule]
  (let [ rule (-> enriched-rule :args)
        good (rule :good)
        item-qty (last good)
        discount-qty (quot item-qty (rule :treshold))
        non-discount-qty (mod item-qty (rule :treshold))]
    (if (nil? good)
      0
      (+ 
        (* discount-qty (rule :discount))
        (* non-discount-qty (rule :unit)))
      )))

(defn clean [basket]
  (filter #(not (= % nil)) basket))

(defrecord Rule [args]
  Rule-Intf
  (apply-discount [this]
                  (calc this)))

(defrecord Checkout [rules basket]
  Checkout-Intf
  (total [this]
         (let [enriched-rule (map  #(attach-rule rules %)
                                  (seq (frequencies basket)))]
              (apply +  (map  #(apply-discount %) 
                            (clean enriched-rule) ) )))
  (scan [this item]
        (update-in this [:basket] conj item)))


