(DATA  y)
(FUNCTION  main  [(int a)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 1)]  [])
    (OPER 5 Add_I [(r 3)]  [(r 3)])
    (OPER 6 Add_I [(r 4)]  [(i 1)(i 2)(r 4)])
    (OPER 7 Div_I [(r 5)]  [(r 1)(i 2)(r 5)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)(r 1)])
  )
)
