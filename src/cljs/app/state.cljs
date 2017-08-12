(ns app.state
  (:require-macros
   [cljs.core.async.macros
    :refer [go go-loop]])
  (:require
   [cljs.core.async :as async
    :refer [<!]]
   [taoensso.timbre :as timbre]
   [reagent.core :as reagent]
   [re-frame.core :as rf]))

(defn init [& [initial]]

  (timbre/debug "[INIT]")

  (rf/reg-event-db
   :initialize
   (fn [db _] initial))

  (rf/reg-sub
   :state
   (fn [db [_ & [path]]]
     (get-in db path)))

  (rf/reg-event-db
   :state
   (fn [db [_ path value]]
     (assoc-in db path value)))

  (rf/reg-sub
   :user
   (fn [db _] (:user db)))

  (rf/reg-event-db
   :page
   (fn [db [_ value & [user]]]
     (assoc db :page value
               :user (keyword (str user)))))

  (rf/reg-sub
   :page
   (fn [db _] (:page db))))
