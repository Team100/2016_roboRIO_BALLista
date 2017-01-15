package org.usfirst.frc100.BALLista.commands;

import edu.wpi.first.wpilibj.command.Command;

public class DoNothing extends Command{
	int num;
	
	public DoNothing(int num){
		super();
		this.num = num;
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		System.out.println(num);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
