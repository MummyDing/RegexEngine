package com.github.mummyding.nfa;

import com.github.mummyding.regex.RegexParse;

public class ScaleState extends State
{
	public boolean[] scale;

	public ScaleState(boolean[] s)
	{
		super(RegexParse.Scale);
		this.scale = s;
	}
}
