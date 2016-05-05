.text
	.align 4
.globl  main
main:
main_bb2:
	movl	$0, %EAX
	cmpl	$1, %EDI
	jne	main_bb6
main_bb5:
	movl	$1, %EAX
main_bb6:
	movl	$1, %EDI
main_bb1:
	ret
