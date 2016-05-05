(DATA  y)
(FUNCTION  main  [(int a)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 3)]  [(i 1)])
    (OPER 5 Add_I [(r 3)]  [(r 3)(i 2)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)(r 1)])
  )
)
