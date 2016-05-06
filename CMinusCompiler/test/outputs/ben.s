.text
	.align 4
.globl  test
test:
test_bb2:
test_bb3:
	movl	$0, %EAX
	movl	$1, %EAX
	movl	$0, %EAX
	movl	%EAX, %ESI
	movl	%EDI, %EAX
	cmpl	%ESI, %EAX
	jle	test_bb5
test_bb4:
	movl	$1, %EDI
	subl	%EDI, %ESI
test_bb6:
	cmpl	%ESI, %EAX
	jne	test_bb16
test_bb14:
	cmpl	%ESI, %EAX
	jne	test_bb16
test_bb17:
	cmpl	%ESI, %EAX
	jne	test_bb20
test_bb19:
	movl	$1, %EAX
	movl	%ESI, %EDI
	subl	%EAX, %EDI
	movl	%EDI, %EAX
	cmpl	%ESI, %EAX
	je	test_bb19
test_bb20:
	cmpl	%ESI, %EAX
	je	test_bb17
test_bb16:
	movl	$3, %EAX
test_bb1:
	ret
test_bb10:
	movl	$1, %EDI
	movl	%EDI, %ESI
	movl	$1, %EDI
	cmpl	%EDI, %ESI
	jne	test_bb11
test_bb12:
	movl	$2, %EDI
	addl	%EDI, %ESI
	movl	$1, %EDI
	cmpl	%EDI, %ESI
	je	test_bb12
test_bb13:
	jmp	test_bb11
test_bb5:
	movl	$2, %EDI
	movl	%EDI, %ESI
	movl	$2, %EDI
	cmpl	%EDI, %ESI
	jne	test_bb6
test_bb7:
	movl	$2, %EDI
	movl	%EDI, %ESI
	movl	$2, %EDI
	cmpl	%EDI, %ESI
	jne	test_bb10
test_bb9:
	movl	$1, %EDI
	addl	%EDI, %ESI
test_bb11:
	movl	$2, %EDI
	cmpl	%EDI, %ESI
	je	test_bb7
test_bb8:
	jmp	test_bb6
