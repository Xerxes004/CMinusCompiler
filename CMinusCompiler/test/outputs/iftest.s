.text
	.align 4
.globl  main
main:
main_bb2:
	movl	$1, %EAX
	cmpl	$1, %EAX
	jne	main_bb7
main_bb6:
main_bb7:
main_bb3:
main_bb5:
	movl	$2, %EAX
main_bb1:
	ret
