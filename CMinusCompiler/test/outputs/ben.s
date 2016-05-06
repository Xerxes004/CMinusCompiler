.text
	.align 4
.globl  main
main:
main_bb2:
main_bb3:
	movl	$0, %EAX
	movl	$1, %EAX
	movl	$0, %EAX
	movl	%EAX, %ESI
	movl	%EDI, %EAX
	cmpl	%ESI, %EAX
	jle	main_bb5
main_bb4:
	movl	$1, %EDI
	subl	%EDI, %ESI
main_bb6:
	cmpl	%ESI, %EAX
	jne	main_bb16
main_bb14:
	cmpl	%ESI, %EAX
	jne	main_bb16
main_bb17:
	cmpl	%ESI, %EAX
	jne	main_bb20
main_bb19:
	movl	$1, %EAX
	movl	%ESI, %EDI
	subl	%EAX, %EDI
	movl	%EDI, %EAX
	cmpl	%ESI, %EAX
	je	main_bb19
main_bb20:
	cmpl	%ESI, %EAX
	je	main_bb17
main_bb16:
	movl	$3, %EAX
main_bb1:
	ret
main_bb10:
	movl	$1, %EDI
	movl	%EDI, %ESI
	movl	$1, %EDI
	cmpl	%EDI, %ESI
	jne	main_bb11
main_bb12:
	movl	$2, %EDI
	addl	%EDI, %ESI
	movl	$1, %EDI
	cmpl	%EDI, %ESI
	je	main_bb12
main_bb13:
	jmp	main_bb11
main_bb5:
	movl	$2, %EDI
	movl	%EDI, %ESI
	movl	$2, %EDI
	cmpl	%EDI, %ESI
	jne	main_bb6
main_bb7:
	movl	$2, %EDI
	movl	%EDI, %ESI
	movl	$2, %EDI
	cmpl	%EDI, %ESI
	jne	main_bb10
main_bb9:
	movl	$1, %EDI
	addl	%EDI, %ESI
main_bb11:
	movl	$2, %EDI
	cmpl	%EDI, %ESI
	je	main_bb7
main_bb8:
	jmp	main_bb6
