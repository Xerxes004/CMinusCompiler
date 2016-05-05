(DATA  y)
(FUNCTION  main  [(int a)]
  (BB 2
    (OPER 3 Func_Entry []  [])
    (OPER 8 Mul_I [(r 6)]  [(i 2)(i 3)])
    (OPER 7 Div_I [(r 5)]  [(r 6)(i 4)])
    (OPER 6 Add_I [(r 4)]  [(i 1)(r 5)])
    (OPER 5 GT [(r 3)]  [(r 4)(i 0)])
    (OPER 4 Mov [(r 1)]  [(r 3)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(r 1)])
  )
)
