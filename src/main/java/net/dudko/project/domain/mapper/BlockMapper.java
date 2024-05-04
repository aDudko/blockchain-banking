package net.dudko.project.domain.mapper;

import net.dudko.project.core.Block;

public class BlockMapper {

    public static net.dudko.project.domain.entity.blockchain.Block mapToBlock(Block blockDto) {
        return net.dudko.project.domain.entity.blockchain.Block.builder()
                .id(blockDto.getId())
                .hash(blockDto.getHash())
                .prevHash(blockDto.getPrevHash())
                .data(blockDto.getData())
                .timeCreate(blockDto.getTimeCreate())
                .nonce(blockDto.getNonce())
                .build();
    }

    public static Block mapToBlockDto(net.dudko.project.domain.entity.blockchain.Block block) {
        return Block.builder()
                .id(block.getId())
                .hash(block.getHash())
                .prevHash(block.getPrevHash())
                .data(block.getData())
                .timeCreate(block.getTimeCreate())
                .nonce(block.getNonce())
                .build();
    }

}
