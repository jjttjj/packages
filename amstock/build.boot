(set-env!
 :resource-paths #{"resources"}
 :dependencies '[[cljsjs/boot-cljsjs "0.5.1"  :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "3.19.2")
(def +version+ (str +lib-version+ "-0"))

(task-options!
 pom  {:project     'cljsjs/amstock
       :version     +version+
       :description "Javascript stock charting"
       :url         "http://www.amcharts.com/"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"License" "https://github.com/amcharts/amstock3/blob/master/licence.txt"}})



;;https://github.com/amcharts/amstock3/archive/3.19.2.zip
(deftask package []
  (comp
   (download :url (str "https://github.com/amcharts/amstock3/archive/"
                       +lib-version+ ".zip")
             :checksum "7A96C5DC2177EF09294AF084CB1E7864"
             :unzip true)
   (sift :move {#".*amcharts\.js$" "cljsjs/amstock/production/amcharts.inc.js"
                #".*amstock\.js$" "cljsjs/amstock/production/amstock.inc.js"
                #".*serial\.js$" "cljsjs/amstock/production/serial.inc.js"
                ;;todo: themes, etc
                })
   (sift :include #{#"^cljsjs"})
   (show :fileset true)
   (deps-cljs :name "cljsjs.amstock")))
