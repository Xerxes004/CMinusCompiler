.data
.comm	y,4,4

.text
	.align 4
.globl  call
call:
call_bb2:
call_bb3:
call_bb1:
	ret
.globl  main
main:
main_bb2:
	pushq	%R15
main_bb3:
	call	call
	movl	%R15D, %EAX
