(DATA  y)
(FUNCTION  main  [(int a)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 3)]  [(i 1)])
    (OPER 5 Mov [(r 1)]  [(r 3)])
    (OPER 7 Mov [(r 5)]  [(i 1)])
    (OPER 6 EQ [(r 4)]  [(r 1)(r 5)])
    (OPER 8 BEQ []  [(r 4)(i 0)(bb 5)])
    (OPER 10 Mov [(r 7)]  [(i 1)])
    (OPER 9 EQ [(r 6)]  [(r 1)(r 7)])
  )
  (BB 4
    (OPER 11 Mov [(r 8)]  [(i 1)])
    (OPER 12 Mov [(r 1)]  [(r 8)])
  )
  (BB 6
  )
)
