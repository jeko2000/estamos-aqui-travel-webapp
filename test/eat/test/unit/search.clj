(ns eat.test.unit.search
  (:require [clojure.test :refer :all]
            [eat.test.test-helper :as help]
            [eat.search :as search]
            [clucy.core :as clucy]))

(deftest search
  (let [index-factory (clucy/memory-index)]
    (clucy/add index-factory {:md "Some searchable text"})
    (binding [search/*index* index-factory]
      (is (= (search/search "Some") '({:md "Some searchable text"})))
      (is (= (search/search "some") '({:md "Some searchable text"})))
      (is (= (search/search "searchable") '({:md "Some searchable text"})))
      (is (= (search/search "text") '({:md "Some searchable text"})))
      (is (= (search/search "unrelated") '())))))

