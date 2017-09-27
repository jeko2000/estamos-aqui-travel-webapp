(ns eat.io-test
  (:require [eat.io :refer :all]
            [midje.sweet :refer :all]))

(fact "`build-path` builds folder or uri paths from parts"
      (build-path) => empty?
      (build-path "") => empty?
      (build-path "folder" "file") => "folder/file")
