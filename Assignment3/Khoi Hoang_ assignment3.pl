/**Khoi Hoang
 * Assignment 3
 */

% question 1: countdown function return a list from n to 1
countdown(0, []) :- !. % base case when first element is 0 return empty list
countdown(H, [H|T]) :- (A is H-1, countdown(A, T)). %recursively call H-1

% question 2: dupList
dupList([], []) :- !. %base case when both list are empty
dupList([H|T1], [H, H|T2]) :- (dupList(T1, T2)).

% question 3: oddSize
oddSize([_]) :- !. %base case, when list has one element, which is odd
oddSize([_,_|L]) :- oddSize(L).

% question 4: append
/*
 ###:- append(X, Y, [1, 2])
    & append([], B, B)

    unify [] = X, B = Y = [1, 2]
    giving :-        i.e. success
    Result is X = [], Y = [1, 2]

    Backtrack to ## we have
    :- append(X, Y, [1, 2])
    & append([Head| TailA], B, [Head| TailC]) :- append(TailA, B, TailC)

    unify [1| TailA] = X, B = Y, Head = 1, TailC = [2]
 ## giving :- append(TailA, Y, [2])
	   & append([], B, B)

    unify [] = TailA, B = Y = [2]
    giving :-	     i.e. success
    Backtrack we have:
          [1| TailA] = [1| [] ] = [1] = X
    Result is X = [1], Y = [2]

    Backtrack to # we have
    :- append(TailA, Y, [2]
    & append([Head'| TailA'], B', [Head'| TailC']) :- append(TailA', B', TailC')

    unify [2| TailA'] = TailA, B' = Y, Head' = 2, TailC' = []
  # giving :- append(TailA', Y, [])
           & append([], B, B)

    unify [] = TailA', B = Y = []
    giving :-	     i.e. success
    Backtrack we have [2| TailA'] = [2| [] ] = [2] = TailA
                 and  [1| TailA] = [1| [2] ] = [1, 2] = X
    Result is X = [1, 2] Y = []

    Backtrack to # append(TailA', Y, []) cannot match with anything
    else, hence failure

    So the possible solutions will be as follow:
    X = [], Y = [1, 2]
    X = [1], Y = [2]
    X = [1, 2], Y = []
    false

*/




