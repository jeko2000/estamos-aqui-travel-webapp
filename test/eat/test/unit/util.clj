(ns eat.test.unit.util
  (:require [clojure.test :refer :all]
            [eat.test.test-helper :as help]
            [eat.util :as util]))

(use-fixtures :each
  help/default-fixture)

(deftest build-path
  (is (= (util/build-path "") ""))
  (is (= (util/build-path "" "") "/"))
  (is (= (util/build-path "dir" "") "dir/"))
  (is (= (util/build-path "" "file") "/file"))
  (is (= (util/build-path "dir" "file") "dir/file"))
  (is (= (util/build-path "dir/" "/file") "dir/file"))
  (is (= (util/build-path "dir/" "file") "dir/file"))
  (is (= (util/build-path "dir" "/file") "dir/file"))
  (is (= (util/build-path "dir1" "dir2" "file") "dir1/dir2/file")))

(deftest title->url
  (is (= (util/title->url "") "/posts/"))
  (is (= (util/title->url "SaMplE") "/posts/sample"))
  (is (= (util/title->url "Contains spaces") "/posts/contains-spaces"))
  (is (= (util/title->url "Contains mul tiple spa ces") "/posts/contains-mul-tiple-spa-ces"))  
  (is (= (util/title->url "Contains áéíóíñ") "/posts/contains-aeioin"))
  (is (= (util/title->url "Contains ~!@#$%^&*()_+={}][|\\") "/posts/contains-")))

(deftest string->keyword
  (is (= (util/string->keyword "a") :a))
  (is (= (util/string->keyword "A") :a))
  (is (= (util/string->keyword "SamPle") :sample))
  (is (= (util/string->keyword "compound_word") :compound-word))
  (is (= (util/string->keyword "compound.word") :compound-word)))
