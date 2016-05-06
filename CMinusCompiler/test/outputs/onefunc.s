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
	movl	$1, %EAX
	cmpl	%EAX, %EDI
	jne	main_bb5
main_bb4:
	movl	$2, %EAX
	cmpl	%EAX, %EDI
	je	main_bb8
main_bb7:
	movl	$2, %EAX
	movl	%EAX, %EDI
main_bb6:
	movl	%EDI, %EAX
main_bb1:
	ret
main_bb8:
	movl	$3, %EAX
	movl	%EAX, %EDI
	jmp	main_bb6
main_bb14:
	movl	$3, %EAX
	movl	%EAX, %EDI
	jmp	main_bb6
main_bb11:
	movl	$2, %EAX
	cmpl	%EAX, %EDI
	je	main_bb14
main_bb13:
	movl	$2, %EAX
	movl	%EAX, %EDI
main_bb15:
	jmp	main_bb6
main_bb5:
	movl	$2, %EAX
	cmpl	%EAX, %EDI
	je	main_bb11
main_bb10:
	movl	$2, %EAX
	movl	%EAX, %EDI
main_bb12:
	jmp	main_bb6
