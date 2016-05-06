(DATA  y)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 2)]  [(i 0)])
    (OPER 5 Mov [(r 1)]  [(r 2)])
    (OPER 7 Mov [(r 4)]  [(i 1)])
    (OPER 6 EQ [(r 3)]  [(r 1)(r 4)])
    (OPER 8 BEQ []  [(r 3)(i 0)(bb 5)])
  )
  (BB 4
    (OPER 10 Mov [(r 6)]  [(i 2)])
    (OPER 9 NEQ [(r 5)]  [(r 1)(r 6)])
    (OPER 11 BEQ []  [(r 5)(i 0)(bb 8)])
  )
  (BB 7
    (OPER 12 Mov [(r 7)]  [(i 2)])
    (OPER 13 Mov [(r 1)]  [(r 7)])
  )
  (BB 9
  )
  (BB 6
    (OPER 32 Mov [(m RetReg)]  [(r 1)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 8
    (OPER 14 Mov [(r 8)]  [(i 3)])
    (OPER 15 Mov [(r 1)]  [(r 8)])
    (OPER 16 Jmp []  [(bb 9)])
  )
  (BB 14
    (OPER 27 Mov [(r 15)]  [(i 3)])
    (OPER 28 Mov [(r 1)]  [(r 15)])
    (OPER 29 Jmp []  [(bb 15)])
  )
  (BB 11
    (OPER 23 Mov [(r 13)]  [(i 2)])
    (OPER 22 NEQ [(r 12)]  [(r 1)(r 13)])
    (OPER 24 BEQ []  [(r 12)(i 0)(bb 14)])
  )
  (BB 13
    (OPER 25 Mov [(r 14)]  [(i 2)])
    (OPER 26 Mov [(r 1)]  [(r 14)])
  )
  (BB 15
    (OPER 30 Jmp []  [(bb 12)])
  )
  (BB 5
    (OPER 18 Mov [(r 10)]  [(i 2)])
    (OPER 17 NEQ [(r 9)]  [(r 1)(r 10)])
    (OPER 19 BEQ []  [(r 9)(i 0)(bb 11)])
  )
  (BB 10
    (OPER 20 Mov [(r 11)]  [(i 2)])
    (OPER 21 Mov [(r 1)]  [(r 11)])
  )
  (BB 12
    (OPER 31 Jmp []  [(bb 6)])
  )
)
