# Changelog

This file should contain all important changes to the project

## 1.0.5
   - Refactor all view logic away from layout and onto route files
   - Create an all-posts page to serve as an archive showing all written posts to date
   - Update post page layout to include link to post's author
   - Leverage http-response library
   - Build reusable branded button component

## 1.0.4
   - Add ability for users to export posts as pdf files
   - Add gradient to the blog's navbar
   - Update admin validator to allow an optional '/' when assigning preview images
   - Allow for admins to activate/deactivate posts
   - Add author pages rendered under /authors/:author routes
   - Implement git releases on project

## 1.0.3
   - Implement asyncronous calls for instagram api
   - Fix issue with index page where Recent Posts component defaults to all posts
   - Add basic unit tests using clojure.test library
   - Introduce cljs support through Reagent
   - Leverage Bouncer library for validation
   - Add validation to Edit and New Post forms
   - Include license and url keys to project.clj

## 1.0.2
   - Add search functionality through Lucene wrapper
   - Add init ns/function to streamline initiation tasks
   - Keywordize all calls to ring parameters
   - Fix issue with handler middleware order
   - Wrap common anti-forgery and other errors onto error layout
   - Add support for Atom feed
   - Add support for previous/next post links
   - Add GA tracking
   - Update default Lucene search field to content/markdown
   - Add CHANGELOG.md file

## 1.0.1
   - Update instagram boost message
   - Add sidebar to about us page
   - Update preview renderer for mobile devices
   - Add favicon to base template
   - Update dev main function to load external resources to aid development
   - Add anti-forgery links to post forms
   - Improve image upload handler to accept multiple files
   - Update disclaimer text

## 1.0.0
   - Main merge from develop branch
   - Add default config to classpath
   - Made css file references static
   - Update io functions to handle image upload

## 0.2.25
   - Add dev profile for dev middleware

## 0.2.24
   - Add AOT setting for Uberjar

## 0.2.23
   - Update resource function to return classpaths
   - Update functions to use external resources

## 0.2.22
   - Implement synchronous calls to instagram api
   - Add layout and routes for About Us page

## 0.2.21
   - Add profiles.clj file to .gitignore
   - Pass sticky footer fix to tag and error pages

## 0.2.20
   - Fix issue with diacretics on urls

## 0.2.19
   - Refactor log namespace

## 0.2.18
   - Implement dev middleware for dev environment only

## 0.2.17
   - Enhance styling on admin pages

## 0.2.16
   - Integrate Admin panel

## 0.2.15
   - Add small logo to navbar

## 0.2.14
   - Increase navbar height from 50px to 72px
   - Add small logo to navbar

## 0.2.13
   - Implement custom hr-link divider

## 0.2.12
   - Improve sticky footer fix

## 0.2.11
   - Implement naive sticky footer fix

## 0.2.10
   - Standardize post styling

## 0.2.9
   - Standardize home route styling

## 0.2.8
   - Create disclaimer page and routes
   - Add `font awesome` CSS library

## 0.2.0
   - Add Stasis dependency and refactor code for static generation

## 0.2.7
   - Implement sql manipulation via restricted routes
   - Enable title image rendering on all posts

## 0.2.6
   - Implement sql migrations for db
   - Add README

## 0.2.5
   - Clean dependency tree

## 0.2.4
   - Enable file upload

## 0.2.3
   - Major refactor to serve fully dynamic blog

## 0.2.2
   - Add dynamic capabilities to blog

## 0.2.1
   - Update stasis urls to end with a "/" per stasis 2.0 change
   - Add core tests for io functions
   - Refactor io to improve hiccup support
   - Add support for tags

## 0.1.1
   - Refactor io functions

## 0.1.0
   - Initial
