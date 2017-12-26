package messages;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikita_Markovnikov on 2/21/2017.
 */
public class DNSMessage {
    private short id;
    private Boolean QR, AA, TC, RD, RA, Z, AD, CD;
    private OpCode opCode;
    private RCode rCode;
    private List<DNSQuery> questions;
    private List<Record> answers;
    private List<Record> serversName;
    private List<Record> additionalRecords;

    @Override
    public String toString() {
        String result = String.format("id = %d, QR = %s, AA = %s, TC = %s, RD = %s, RA = %s, Z = %s, AD = %s, CD = %s, opCode = %s, rCode = %s\n",
                id, QR, AA, TC, RD, RA, Z, AD, CD, opCode, rCode);

        result += "questions:\n";
        for (DNSQuery query: questions) {
            result += "\t" + query.toString() + "\n";
        }
        result += "answers:\n";
        for (Record record: answers) {
            result += "\t" + record.toString() + "\n";
        }
        result += "servers names:\n";
        for (Record record: serversName) {
            result += "\t" + record.toString() + "\n";
        }
        result += "additional records:\n";
        for (Record record: additionalRecords) {
            result += "\t" + record.toString() + "\n";
        }
        return result;

    }

    public short getId() {
        return id;
    }

    public Boolean getQR() {
        return QR;
    }

    public Boolean getAA() {
        return AA;
    }

    public Boolean getTC() {
        return TC;
    }

    public Boolean getRD() {
        return RD;
    }

    public Boolean getRA() {
        return RA;
    }

    public Boolean getZ() {
        return Z;
    }

    public Boolean getAD() {
        return AD;
    }

    public Boolean getCD() {
        return CD;
    }

    public OpCode getOpCode() {
        return opCode;
    }

    public RCode getRCode() {
        return rCode;
    }

    public List<DNSQuery> getQuestions() {
        return questions;
    }

    public List<Record> getAnswers() {
        return answers;
    }

    public List<Record> getServersName() {
        return serversName;
    }

    public List<Record> getAdditionalRecords() {
        return additionalRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DNSMessage that = (DNSMessage) o;

        if (id != that.id) return false;
        if (!QR.equals(that.QR)) return false;
        if (!AA.equals(that.AA)) return false;
        if (!TC.equals(that.TC)) return false;
        if (!RD.equals(that.RD)) return false;
        if (!RA.equals(that.RA)) return false;
        if (!Z.equals(that.Z)) return false;
        if (!AD.equals(that.AD)) return false;
        if (!CD.equals(that.CD)) return false;
        if (opCode != that.opCode) return false;
        if (rCode != that.rCode) return false;
        if (!questions.equals(that.questions)) return false;
        if (!answers.equals(that.answers)) return false;
        if (!serversName.equals(that.serversName)) return false;
        return additionalRecords.equals(that.additionalRecords);
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + QR.hashCode();
        result = 31 * result + AA.hashCode();
        result = 31 * result + TC.hashCode();
        result = 31 * result + RD.hashCode();
        result = 31 * result + RA.hashCode();
        result = 31 * result + Z.hashCode();
        result = 31 * result + AD.hashCode();
        result = 31 * result + CD.hashCode();
        result = 31 * result + opCode.hashCode();
        result = 31 * result + rCode.hashCode();
        result = 31 * result + questions.hashCode();
        result = 31 * result + answers.hashCode();
        result = 31 * result + serversName.hashCode();
        result = 31 * result + additionalRecords.hashCode();
        return result;
    }

    public enum OpCode {
        QUERY(0),
        NOTIFY(4),
        UPDATE(5);

        private final short value;

        OpCode(int value) {
            this.value = (short) value;
        }

        public static OpCode parse(short i) {
            for (OpCode t : values()) {
                if (t.value == i) return t;
            }
            return null;
        }

        public short value() {
            return value;
        }
    }

    public enum RCode {
        NO_ERROR(0),
        FORM_ERR(1),
        SERV_FAIL(2),
        NX_DOMAIN(3),
        NOT_IMP(4),
        REFUSED(5);

        private final short value;

        RCode(int value) {
            this.value = (short) value;
        }

        public static RCode parse(short i) {
            for (RCode t : values()) {
                if (t.value == i) return t;
            }
            return null;
        }

        public short value() {
            return value;
        }
    }

    public static Builder newBuilder() {
        return new DNSMessage().new Builder();
    }

    private DNSMessage() {
        id = 1;
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        serversName = new ArrayList<>();
        additionalRecords = new ArrayList<>();
        opCode = OpCode.QUERY;
        rCode = RCode.NO_ERROR;
        QR = AA = TC = RD = RA = Z = AD = CD = false;
    }

    public class Builder {
        private Builder() {
        }

        public Builder addDNSQuery(DNSQuery dnsQuery) {
            DNSMessage.this.questions.add(dnsQuery);
            return this;
        }

        public Builder addDNSQueries(List<DNSQuery> dnsQueries) {
            DNSMessage.this.questions.addAll(dnsQueries);
            return this;
        }

        public Builder addAnswer(Record answer) {
            DNSMessage.this.answers.add(answer);
            return this;
        }

        public Builder addAnswers(List<Record> answers) {
            DNSMessage.this.answers.addAll(answers);
            return this;
        }

        public Builder addServersName(Record serversName) {
            DNSMessage.this.serversName.add(serversName);
            return this;
        }

        public Builder addServersNames(List<Record> serversNames) {
            DNSMessage.this.serversName.addAll(serversNames);
            return this;
        }

        public Builder addAdditionalRecord(Record record) {
            DNSMessage.this.additionalRecords.add(record);
            return this;
        }

        public Builder addAdditionalRecords(List<Record> records) {
            DNSMessage.this.additionalRecords.addAll(records);
            return this;
        }

        public Builder setQR(Boolean value) {
            DNSMessage.this.QR = value;
            return this;
        }

        public Builder setAA(Boolean value) {
            DNSMessage.this.AA = value;
            return this;
        }

        public Builder setTC(Boolean value) {
            DNSMessage.this.TC = value;
            return this;
        }

        public Builder setRD(Boolean value) {
            DNSMessage.this.RD = value;
            return this;
        }

        public Builder setRA(Boolean value) {
            DNSMessage.this.RA = value;
            return this;
        }

        public Builder setZ(Boolean value) {
            DNSMessage.this.Z = value;
            return this;
        }

        public Builder setAD(Boolean value) {
            DNSMessage.this.AD = value;
            return this;
        }

        public Builder setCD(Boolean value) {
            DNSMessage.this.CD = value;
            return this;
        }

        public Builder setOpCode(OpCode opCode) {
            DNSMessage.this.opCode = opCode;
            return this;
        }

        public Builder setRCode(RCode rCode) {
            DNSMessage.this.rCode = rCode;
            return this;
        }

        public Builder setId(short id) {
            DNSMessage.this.id = id;
            return this;
        }

        public DNSMessage build() {
            return DNSMessage.this;
        }
    }

    public ByteBuffer toByteBuffer() {
        final ByteBuffer request = ByteBuffer.allocate(2048);
        request.putShort(id);
        short flags = 0;
        if (QR) {
            flags |= (1 << 15);
        }

        if (AA) {
            flags |= (1 << 10);
        }

        if (TC) {
            flags |= (1 << 9);
        }

        if (RD) {
            flags |= (1 << 8);
        }

        if (RA) {
            flags |= (1 << 7);
        }

        if (Z) {
            flags |= (1 << 6);
        }

        if (AD) {
            flags |= (1 << 5);
        }

        if (CD) {
            flags |= (1 << 4);
        }

        switch (opCode) {
            case QUERY:
                break;
            case NOTIFY: {
                flags |= (1 << 13);
                break;
            }
            case UPDATE: {
                flags |= (1 << 13);
                flags |= (1 << 11);
                break;
            }
        }

        switch (rCode) {
            case NO_ERROR:
                break;
            case FORM_ERR: {
                flags |= 1;
                break;
            }
            case SERV_FAIL: {
                flags |= (1 << 1);
                break;
            }
            case NX_DOMAIN: {
                flags |= (1 << 1);
                flags |= 1;
                break;
            }
            case NOT_IMP: {
                flags |= (1 << 2);
                break;
            }
            case REFUSED: {
                flags |= (1 << 2);
                flags |= 1;
                break;
            }
        }

        request.putShort(flags);
        short qdcount = (short) (questions.size());
        request.putShort(qdcount);
        short ancount = (short) (answers.size());
        request.putShort(ancount);
        short nscount = (short) (serversName.size());
        request.putShort(nscount);
        short arcount = (short) (additionalRecords.size());
        request.putShort(arcount);

        questions.forEach(q -> request.put(q.toByteBuffer()));
        answers.forEach(a -> request.put(a.toByteBuffer()));
        serversName.forEach(s -> request.put(s.toByteBuffer()));
        additionalRecords.forEach(a -> request.put(a.toByteBuffer()));

        return (ByteBuffer) request.flip();
    }

    public static DNSMessage parse(ByteBuffer buffer) {
        short id = buffer.getShort();
        short flags = buffer.getShort();
        short qdcount = buffer.getShort(), ancount = buffer.getShort(),
                nscount = buffer.getShort(), arcount = buffer.getShort();

        Builder builder = DNSMessage.newBuilder();
        builder.setId(id);

        builder.setQR(((flags >> 15) & 1) == 1);
        builder.setAA(((flags >> 10) & 1) == 1);
        builder.setTC(((flags >> 9) & 1) == 1);
        builder.setRD(((flags >> 8) & 1) == 1);
        builder.setRA(((flags >> 7) & 1) == 1);
        builder.setZ(((flags >> 6) & 1) == 1);
        builder.setAD(((flags >> 5) & 1) == 1);
        builder.setCD(((flags >> 4) & 1) == 1);

        short rCode = (short) ((flags & 1) + 2 * ((flags >> 1) & 1) + 4 * ((flags >> 2) & 1));
        builder.setRCode(RCode.parse(rCode));

        short opCode = (short) (((flags >> 11) & 1) + 2 * ((flags >> 12) & 1) + 4 * ((flags >> 13) & 1));
        builder.setOpCode(OpCode.parse(opCode));

        for(int i = 0 ; i < qdcount; i++ ) {
            builder.addDNSQuery(DNSQuery.parse(buffer));
        }

        for(int i = 0 ; i < ancount; i++ ) {
            builder.addAnswer(Record.parse(buffer));
        }

        for(int i = 0 ; i < nscount; i++ ) {
            builder.addServersName(Record.parse(buffer));
        }

        for(int i = 0 ; i < arcount; i++ ) {
            builder.addAdditionalRecord(Record.parse(buffer));
        }

        return builder.build();
    }
}
