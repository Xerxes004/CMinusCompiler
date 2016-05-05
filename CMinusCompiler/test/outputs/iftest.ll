(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
    (OPER 5 EQ [(r 2)]  [(i 1)(i 1)])
    (OPER 4 BNE []  [()
(r 2)(bb 4)])
  )
  (BB 3
    (OPER 6 Mov [(r 1)]  [(i 1)])
  )
  (BB 5
    (OPER 8 Mov [(r 1)]  [(i 2)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(r 1)])
  )
)
