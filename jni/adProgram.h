/*
 * adProgram.h
 *
 *  Created on: 2013-4-18
 *      Author: unknown
 */

#ifndef ADPROGRAM_H_
#define ADPROGRAM_H_

class adProgram;

class adProgram
{
public:
	int ProgramHandle;
	int ModelMatrixSlot;
	int TextureSlot;
	int PositionSlot;
	int ColorSlot;
	int NormalSlot;
	int TexCoordSlot;
};

#endif /* ADPROGRAM_H_ */
