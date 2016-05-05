(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
    (OPER 5 EQ [(r 2)]  [(r 1)(i 1)])
    (OPER 4 Mov [(r 1)]  [(i 1)(r 2)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(r 1)])
  )
)
