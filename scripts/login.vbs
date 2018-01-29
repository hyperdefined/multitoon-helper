Set oFSO = CreateObject("Scripting.FileSystemObject")
strFolder = oFSO.GetParentFolderName(WScript.ScriptFullName)
Set WshShell = WScript.CreateObject("WScript.Shell") 
WSHShell.Run "cmd /c" & """" & strFolder & "\login.bat"" " & WScript.Arguments(0) & " " & WScript.Arguments(1), 0, False
Set WshShell = Nothing