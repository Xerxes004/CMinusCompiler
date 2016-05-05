(DATA  a)
(FUNCTION  addThem  [(int d) (int e)]
  (BB 2
    (OPER 3 Func_Entry []  [])
    (OPER 5 Add_I [(r 4)]  [(r 2)(r 3)])
    (OPER 4 Mov [(r 1)]  [(r 4)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(r 1)])
  )
)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
    (OPER 4 Mov [(r 1)]  [(i 5)])
    (OPER 5 Mov [(r 3)]  [(i 0)])
    (OPER 6 Mov [(r 5)]  [(i 1)])
    (OPER 8 Div_I [(r 6)]  [(r 3)(i 3)])
    (OPER 7 Mov [(r 4)]  [(r 6)])
    (OPER 10 Mul_I [(r 7)]  [(r 4)(i 4)])
    (OPER 9 Mov [(r 3)]  [(r 7)])
    (OPER 11 Mov [(r 2)]  [])
    (OPER 12 Jmp [(bb 3)]  [])
  )
  (BB 3
    (OPER 13 Jmp [(bb 4)]  [])
  )
  (BB 4
    (OPER 14 Jmp [(bb 5)]  [])
  )
  (BB 5
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(i 0)])
  )
)
