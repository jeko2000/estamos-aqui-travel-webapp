(ns eat.io-test
  (:require [eat.io :refer :all]
            [midje.sweet :refer :all]))

(def sample-doc "resources/2017-01-01-sample.md")
(def sample-doc-no-meta "resources/2017-01-01-sample-no-meta.md")
(def sample-doc-non-existent "not-to-be-found/doc.md")

(def sample-doc-file (get-resource sample-doc))
(def sample-doc-no-meta-file (get-resource sample-doc-no-meta))

(def sample-file-name "2017-01-01-sample.md")
(def sample-prefix "sample")

(defn file? [obj]
  (= (type obj) java.io.File))

(defn files? [objs]
  (every? file? objs))

(facts "about io"
       (fact "`build-path` builds folder or uri paths from parts"
             (build-path) => empty?
             (build-path "") => empty?
             (build-path "folder" "file") => "folder/file"
             (build-path "folder/" "////file") => "folder/file")

       (fact "`get-resource` returns a file object for the given resource if it exists and nil otherwise"
             (get-resource sample-doc) => file?
             (get-resource sample-doc-non-existent) => nil?)

       (fact "`read-resource` will return string of file's contents if it exists and an empty string otherwise"
             (read-resource sample-doc) => "{:title \"Sample\"}\n\n## Sample heading"
             (read-resource sample-doc-non-existent) => empty?)

       (fact "`get-contents` should return a coll of file objects under root and an empty list otherwise"
             (get-contents "resources/") => files?
             (get-contents "malformed/") => empty?)

       (fact "`parse-url` should return a url for a given file-name while removing its extension"
             (parse-url sample-file-name sample-prefix) => "sample/2017-01-01-sample/")

       (fact "`read-file-metadata` should return a clojure structure for metadata found atop a given file.
             Sould also throw AssertionError in case metadata isn't found"
             (read-file-metadata sample-doc-file) => map?
             (read-file-metadata sample-doc-no-meta-file) => (throws java.lang.AssertionError))
       (fact "`read-file-content-without-metadata` should return content from file without metadata"
             (read-file-content-without-metadata sample-doc-file) => "\n\n## Sample heading"
             (read-file-content-without-metadata sample-doc-no-meta-file) => "## I have no metadata")

       (fact "`build-post-map` should return a clojure map representing a given post"
             (build-post-map sample-doc-file) => map?))
