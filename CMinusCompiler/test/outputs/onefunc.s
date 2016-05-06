.data
.comm	y,4,4

.text
	.align 4
.globl  main
main:
main_bb2:
main_bb3:
	movl	$0, %EAX
	movl	%EAX, %EDI
	movl	$2, %EAX
	cmpl	%EAX, %EDI
	je	main_bb1
main_bb4:
	movl	$1, %EAX
	addl	%EAX, %EDI
	movl	$2, %EAX
	cmpl	%EAX, %EDI
	jne	main_bb4
main_bb1:
	ret
