package com.tcn.cosmoslibrary.common.nbt;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Nullable;

import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.TagTypes;

public class CosmosNBTIOHandler {

	public static void write(CompoundTag compoundTag, File file) throws IOException {
		FileOutputStream fileoutputstream = new FileOutputStream(file);

		try {
			DataOutputStream dataoutputstream = new DataOutputStream(fileoutputstream);

			try {
				write(compoundTag, dataoutputstream);
			} catch (Throwable throwable2) {
				try {
					dataoutputstream.close();
				} catch (Throwable throwable1) {
					throwable2.addSuppressed(throwable1);
				}

				throw throwable2;
			}

			dataoutputstream.close();
		} catch (Throwable throwable3) {
			try {
				fileoutputstream.close();
			} catch (Throwable throwable) {
				throwable3.addSuppressed(throwable);
			}

			throw throwable3;
		}

		fileoutputstream.close();
	}

	@Nullable
	public static CompoundTag read(File file) throws IOException {
		if (!file.exists()) {
			return null;
		} else {
			FileInputStream fileinputstream = new FileInputStream(file);

			CompoundTag compoundtag;
			try {
				DataInputStream datainputstream = new DataInputStream(fileinputstream);

				try {
					compoundtag = read(datainputstream, NbtAccounter.unlimitedHeap());
				} catch (Throwable throwable2) {
					try {
						datainputstream.close();
					} catch (Throwable throwable1) {
						throwable2.addSuppressed(throwable1);
					}

					throw throwable2;
				}

				datainputstream.close();
			} catch (Throwable throwable3) {
				try {
					fileinputstream.close();
				} catch (Throwable throwable) {
					throwable3.addSuppressed(throwable);
				}

				throw throwable3;
			}

			fileinputstream.close();
			return compoundtag;
		}
	}

	public static CompoundTag read(DataInput dataInput) throws IOException {
		return read(dataInput, NbtAccounter.unlimitedHeap());
	}

	public static CompoundTag read(DataInput dataInput, NbtAccounter accounter) throws IOException {
		Tag tag = readUnnamedTag(dataInput, accounter);
		if (tag instanceof CompoundTag) {
			return (CompoundTag) tag;
		} else {
			throw new IOException("Root tag must be a named compound tag");
		}
	}

	public static void write(CompoundTag compoundTag, DataOutput dataOutput) throws IOException {
		writeUnnamedTag(compoundTag, dataOutput);
	}

	public static void parse(DataInput dataInput, StreamTagVisitor visitor, NbtAccounter accounter) throws IOException {
		TagType<?> tagtype = TagTypes.getType(dataInput.readByte());
		if (tagtype == EndTag.TYPE) {
			if (visitor.visitRootEntry(EndTag.TYPE) == StreamTagVisitor.ValueResult.CONTINUE) {
				visitor.visitEnd();
			}
		} else {
			switch (visitor.visitRootEntry(tagtype)) {
			case HALT:
			default:
				break;
			case BREAK:
				StringTag.skipString(dataInput);
				tagtype.skip(dataInput, NbtAccounter.unlimitedHeap());
				break;
			case CONTINUE:
				StringTag.skipString(dataInput);
				tagtype.parse(dataInput, visitor, accounter);
			}
		}
	}

	public static void writeUnnamedTag(Tag tag, DataOutput dataOutput) throws IOException {
		dataOutput.writeByte(tag.getId());
		if (tag.getId() != 0) {
			dataOutput.writeUTF("");
			tag.write(dataOutput);
		}
	}

	private static Tag readUnnamedTag(DataInput dataInput, NbtAccounter accounter) throws IOException {
		byte b0 = dataInput.readByte();
		accounter.accountBytes(8);
		if (b0 == 0) {
			return EndTag.INSTANCE;
		} else {
			accounter.readUTF(dataInput.readUTF());
			accounter.accountBytes(32);

			try {
				return TagTypes.getType(b0).load(dataInput, accounter);
			} catch (IOException ioexception) {
				CrashReport crashreport = CrashReport.forThrowable(ioexception, "Loading NBT data");
				CrashReportCategory crashreportcategory = crashreport.addCategory("NBT Tag");
				crashreportcategory.setDetail("Tag type", b0);
				throw new ReportedException(crashreport);
			}
		}
	}
}
