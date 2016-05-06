(FUNCTION  gcd  [(int u) (int v)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 5 Mov [(r 4)]  [(i 0)])
    (OPER 4 NEQ [(r 3)]  [(r 2)(r 4)])
    (OPER 6 BEQ []  [(r 3)(i 0)(bb 5)])
  )
  (BB 4
    (OPER 7 Mov [(m RetReg)]  [(r 1)])
  )
  (BB 6
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 5
    (OPER 9 Pass []  [(r 2)])
    (OPER 12 Div_I [(r 7)]  [(r 1)(r 2)])
    (OPER 11 Mul_I [(r 6)]  [(r 7)(r 2)])
    (OPER 10 Sub_I [(r 5)]  [(r 1)(r 6)])
    (OPER 13 Pass []  [(r 5)])
    (OPER 14 JSR []  [(s gcd)])
    (OPER 15 Mov [(m RetReg)]  [(r 8)])
    (OPER 16 Mov [(m RetReg)]  [(r 8)])
    (OPER 18 Jmp []  [(bb 6)])
  )
)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 JSR []  [(s input)])
    (OPER 5 Mov [(m RetReg)]  [(r 3)])
    (OPER 6 Mov [(r 1)]  [(r 3)])
    (OPER 7 JSR []  [(s input)])
    (OPER 8 Mov [(m RetReg)]  [(r 4)])
    (OPER 9 Mov [(r 2)]  [(r 4)])
    (OPER 10 Pass []  [(r 1)])
    (OPER 11 Pass []  [(r 2)])
    (OPER 12 JSR []  [(s gcd)])
    (OPER 13 Mov [(m RetReg)]  [(r 5)])
    (OPER 14 Pass []  [(r 5)])
    (OPER 15 JSR []  [(s output)])
    (OPER 16 Mov [(m RetReg)]  [(r 6)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
