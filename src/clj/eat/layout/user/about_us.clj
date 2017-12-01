(ns eat.layout.user.about-us
  (:require [eat.layout.user.base :refer [base]]
            [eat.layout.components :refer [post-header static-image]]
            [eat.db :refer [find-tags]]))

(defn about-us-content []
  [:div {:id "about-us-content"}
   (static-image "/img/aboutus_1_low.jpg" "Shell and Johnny in Montreal, Canada")
   [:p "Over a slice of pizza in Buenos Aires—because all good conversations are had over pizza—Shell and Johnny began dreaming of a trip that would take them to 9 South American countries in one year. After finishing a degree and quitting two jobs, they embarked on this journey the morning of August 24, 2017, and made Colombia their first destination. Of course, the couple decided to document their voyage in order to keep their parents informed of their whereabouts. However, since one set of parents speaks Spanish and the other English, they had to design a journal that incorporated both languages into their storytelling. Thus was born "
    [:strong "Estamos Aquí Travel"]", a bilingual blog that shares the adventures, and misadventures, of a Colombian-American couple on a mission to see the world."]
   [:h2 "Who are we?"]
   [:p
    (static-image "/img/aboutus_2_low.jpg" "Shell in Punmamarca, Argentina")]
   [:p [:strong "NAME: "] "Shell"]
   [:p [:strong "PLACE OF BIRTH: "] "Ann Arbor, Michigan, USA"]
   [:p [:strong "LANGUAGES: "] "English, Spanish, Portuguese"]
   [:p [:strong "OCCUPATION BEFORE THE GAP YEAR: "] "Spanish Teacher"]
   [:p [:strong "HOBBIES: "] "reading historical fiction, streaming television shows, dancing salsa (and bachata, and merengue, and cumbia, and…)"]
   [:p [:strong "COUNTRIES VISITED: "] "China, Argentina, Uruguay, Colombia, Spain, Canada, Ecuador"]
   [:p [:strong "FAVORITE THINGS TO DO WHILE TRAVELLING: "] "taking pictures, listening to live music, trying new foods"]
   [:p [:strong "FAVORITE TRAVEL MEMORY: "] "renting a car in Mendoza, Argentina and driving through the Andes Mountains up to the Chilean border"]
   [:p
    (static-image "/img/aboutus_3_low.jpg" "Johnny just outside of Mendoza, Argentina")]
   [:p [:strong "NAME: "] "Johnny"]
   [:p [:strong "PLACE OF BIRTH: "] "Medellín, Antioquia, Colombia"]
   [:p [:strong "LANGUAGES: "] "Spanish, English, Russian"]
   [:p [:strong "OCCUPATION BEFORE THE GAP YEAR: "] "Marketing Manager"]
   [:p [:strong "HOBBIES: "] "functional programming, philosophy, and chess"]
   [:p [:strong "COUNTRIES VISITED: "] "USA, Argentina, Uruguay, UK, France, Spain, Canada, Ecuador"]
   [:p [:strong "FAVORITE THINGS TO DO WHILE TRAVELLING: "] "people watching, trying craft beer, play pick-up chess"]
   [:p [:strong "FAVORITE TRAVEL MEMORY: "] "planning and executing a family trip to the Andalucía region in Spain"]])

(defn about-us-main []
  [:div {:class "main"}
   (about-us-content)])

(defn about-us-page [layout-config posts]
  (base layout-config
        {:title "About Us"
         :pre-content (post-header {:title_img "/img/aboutus_title.jpg" :title "About us"})
         :content (about-us-main)
         :posts posts
         :tags (find-tags posts)}))
