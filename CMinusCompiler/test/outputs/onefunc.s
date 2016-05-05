.data
.comm	y,4,4

.text
	.align 4
.globl  main
main:
main_bb2:
	movl	$2, %EAX
	movl	$3, %EDI
	imull	%EDI, %EAX
	movl	$0, %EDX
	movl	$4, %EDI
	idivl	%EDI, %EAX
	movl	$1, %EDI
	addl	%EAX, %EDI
	movl	$0, %EAX
	cmpl	$0, %EDI
	jle	main_bb4
main_bb3:
	movl	$1, %EAX
main_bb4:
main_bb1:
	ret
