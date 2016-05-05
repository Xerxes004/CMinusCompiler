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
	movl	%EDI, %EAX
	addl	$1, %EAX
	movl	%EAX, %ESI
	movl	$1, y(%RIP)
	movl	y(%RIP), %EAX
	movl	%ESI, %EDI
	addl	%EAX, %EDI
	movl	%EDI, y(%RIP)
main_bb1:
	movl	y(%RIP), %EAX
	movl	y(%RIP), %EDI
	imull	%EDI, %EAX
	movl	%ESI, %EDI
	addl	%EAX, %EDI
	ret
