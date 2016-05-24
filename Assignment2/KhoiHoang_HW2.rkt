;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname KhoiHoang_HW2) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #f)))
; Khoi Hoang
; CSC 135 - HW2

; problem A
(define (atom? X) (not (list? X))) ;define atom? function to check if X is an atom

(define (comparestructlist A B)
  (cond ((and (null? A) (null? B)) #T) ; if both lists are null return true
        ((null? A) #F) ;return false if either list is null and the other is not
        ((null? B) #F)
        ((and (atom? (car A)) (atom? (car B))) ; if both car of two lists are atom then compare the cdr of both lists
             (comparestructlist (cdr A) (cdr B))) 
        ((atom? (car A)) #F) ; if (car of one list) is an atom and the other is not then return false
        ((atom? (car B)) #F)
        (else (and (comparestructlist (car A) (car B)) (comparestructlist (cdr A) (cdr B))))));otherwise recursively compare (car of both lists) and (cdr of both lists)

; problem B
(define (takeoutatom atom listB)
 (cond ((null? listB) '());return null if list is null
       ((equal? atom (car listB)) (takeoutatom atom (cdr listB))) ;if atom is the same as first element of list then recursively remove atom from cdr of list
       ((list? (car listB)) (cons (takeoutatom atom (car listB)) (takeoutatom atom (cdr listB))));if (car list) is a list then recursively remove atom from car and cdr of list
       (else (cons (car listB) (takeoutatom atom (cdr listB)))))) ;otherwise make a list of (car list) and recursively call to remove atom from (cdr list) 

; problem C
(define (changeatom atom1 atom2 listC)
  (cond ((null? listC) '());return null if list if null
        ((equal? atom1 (car listC)) (cons atom2 (changeatom atom1 atom2 (cdr listC))));logic is almost the same as the problem B, if (car list) == atom1, then replace atom1 with atom2 and continue with (cdr list)
        ((list? (car listC)) (cons (changeatom atom1 atom2 (car listC)) (changeatom atom1 atom2 (cdr listC))));recursively replace atom1 with atom2 from car and cdr of list
        (else (cons (car listC) (changeatom atom1 atom2 (cdr listC)))))) ;otherwise if nothing in (car list) recursively replace atom1 and atom2 in (cdr list)

; problem D
(define (lastElement listD) ;function to return the last element of a list, assuming the list is no null
  (if (null? (cdr listD)) (car listD) ; if the (cdr listD) is null then we simply return the first element (car listD)
      (lastElement (cdr listD)))) ; if not then continue to call recursively lastElement(cdr listD)

(define (is-the-end-ok function listD)
  (function (lastElement listD))) ; evaluate function with the last element of listD

; problem E
(define (findMax list1 list2) ;funtion to find the greater of two number in parameter, return list1 if list1>list2 and vice versa
  (if (> list1 list2) list1 list2))

(define (depth listE)
  (cond ((null? listE) 0) ;if list is null return 0, base case
        ((atom? listE) 0) ;if list is just an atom also return 0, also a base case
        (else (findMax (+ 1 (depth(car listE))) (depth(cdr listE)))))) ;otherwise return the greater of recursive call (depth(car list)) +1 or (depth(cdr list))
        ;the logic here using findMax is that to see which one of the recursion is deeper, and everytime car is called we add 1 to the depth

; problem F
(define (positive x) ;helper function, evaluate to x+y 
    (lambda (y) (+ x y)))

(define (negative x) ;helper function, evaluate to x*y
    (lambda (y) (* x y)))

(define (zero x) ; helper function, evaluate to x^2
    (lambda (x) (* x x)))

(define (helper d) ;helper function
    (cond ((> d 0) (positive d)) ;addition when positive number
          ((< d 0) (negative (* -1 d))) ;multiplication when negative number, note that multiply only the number not the sign
          ((= d 0) (zero d)))) ;square if zero

(define (buildSeries listF) ;since the function return a function, when list is null cannot return zero
  (cond ((null? listF) (lambda(x) x)) ;instead when list is null return a function that is the list itself
        (else (compose (buildSeries (cdr listF)) (helper (car listF)))))) ;otherwise recursively apply helper function to all element in the list using compose
  
          
      



         
      