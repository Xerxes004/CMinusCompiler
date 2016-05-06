.text
	.align 4
.globl  gcd
gcd:
gcd_bb2:
	pushq	%R15
	movl	%EDI, %ECX
	movl	%ESI, %EDI
gcd_bb3:
	movl	$0, %EAX
	cmpl	%EAX, %EDI
	je	gcd_bb5
gcd_bb4:
	movl	%ECX, %EAX
gcd_bb1:
	popq	%R15
	ret
gcd_bb5:
	movl	$0, %EDX
	movl	%ECX, %EAX
	idivl	%EDI, %EAX
	imull	%EDI, %EAX
	movl	%ECX, %EDI
	subl	%EAX, %EDI
	movl	%EDI, %ESI
	call	gcd
	movl	%R15D, %EAX
	movl	%R15D, %EAX
	jmp	gcd_bb1
.globl  main
main:
main_bb2:
	pushq	%R12
	pushq	%R13
	pushq	%R14
	pushq	%R15
main_bb3:
	call	input
	movl	%R12D, %EAX
	call	input
	movl	%R13D, %EAX
	movl	%R13D, %EAX
	movl	%R12D, %EDI
	movl	%EAX, %ESI
	call	gcd
	movl	%R14D, %EAX
	movl	%R14D, %EDI
	call	output
	movl	%R15D, %EAX
main_bb1:
	popq	%R15
	popq	%R14
	popq	%R13
	popq	%R12
	ret
